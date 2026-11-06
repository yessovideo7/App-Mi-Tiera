package com.example.playback

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.data.RadioRepository
import com.example.model.PlaybackState
import com.example.model.PodcastEpisode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

object RadioPlayerController {

    private var player: ExoPlayer? = null
    private var appContext: Context? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var sleepTimerJob: Job? = null
    private var progressTrackingJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _volume = MutableStateFlow(1f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private var songMetadataJob: Job? = null

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            updateState { it.copy(isPlaying = isPlaying) }
            if (isPlaying && _playbackState.value.isLiveRadio) {
                startSongMetadataPolling()
            }
        }

        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_BUFFERING -> {
                    updateState { it.copy(isBuffering = true, isError = false) }
                }
                Player.STATE_READY -> {
                    updateState {
                        it.copy(
                            isBuffering = false,
                            isError = false,
                            durationMs = player?.duration?.coerceAtLeast(0L) ?: 0L
                        )
                    }
                }
                Player.STATE_ENDED -> {
                    updateState { it.copy(isPlaying = false, isBuffering = false) }
                }
                Player.STATE_IDLE -> {
                    updateState { it.copy(isBuffering = false) }
                }
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            error.printStackTrace()
            updateState {
                it.copy(
                    isError = true,
                    isBuffering = false,
                    isPlaying = false,
                    errorMessage = "Error de conexión con la emisora. Reintentando..."
                )
            }
            // Auto reconnect logic
            scope.launch {
                delay(3500)
                if (_playbackState.value.isLiveRadio) {
                    retryLiveStream()
                }
            }
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            val title = mediaMetadata.title?.toString()
            if (!title.isNullOrBlank() && title != RadioRepository.STATION_NAME) {
                val parsed = NowPlayingFetcher.parseRawTrack(title)
                updateState {
                    it.copy(
                        nowPlayingSong = parsed.song,
                        nowPlayingArtist = parsed.artist
                    )
                }
            }
        }
    }

    fun initService(context: Context) {
        if (appContext == null) {
            appContext = context.applicationContext
            try {
                val intent = Intent(context, RadioPlaybackService::class.java)
                context.startService(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startServiceIntent(context: Context) {
        try {
            val intent = Intent(context, RadioPlaybackService::class.java)
            ContextCompat.startForegroundService(context, intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPlayer(exoPlayer: ExoPlayer, context: Context) {
        this.player?.removeListener(playerListener)
        this.player = exoPlayer
        this.appContext = context.applicationContext
        exoPlayer.addListener(playerListener)
        exoPlayer.volume = _volume.value

        updateState {
            it.copy(
                isPlaying = exoPlayer.isPlaying,
                isBuffering = exoPlayer.playbackState == Player.STATE_BUFFERING
            )
        }

        startProgressTracking()
    }

    fun clearPlayer() {
        this.player?.removeListener(playerListener)
        this.player = null
        stopProgressTracking()
    }

    private fun updateState(transform: (PlaybackState) -> PlaybackState) {
        _playbackState.value = transform(_playbackState.value)
    }

    fun playLiveRadio() {
        val ctx = appContext ?: return
        startServiceIntent(ctx)

        val exo = player
        if (exo == null) {
            Handler(Looper.getMainLooper()).postDelayed({ playLiveRadio() }, 400)
            return
        }

        val metadata = MediaMetadata.Builder()
            .setTitle(RadioRepository.STATION_NAME)
            .setArtist("En Vivo • Santa Rosa de Osos")
            .setAlbumTitle(RadioRepository.STATION_SLOGAN)
            .setDisplayTitle(RadioRepository.STATION_NAME)
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(RadioRepository.LIVE_STREAM_URL)
            .setMediaMetadata(metadata)
            .build()

        exo.stop()
        exo.clearMediaItems()
        exo.setMediaItem(mediaItem)
        exo.prepare()
        exo.playWhenReady = true
        startSongMetadataPolling()

        updateState {
            it.copy(
                isLiveRadio = true,
                activeEpisodeId = null,
                currentTitle = RadioRepository.STATION_NAME,
                currentSubtitle = "En Vivo • Santa Rosa de Osos",
                isError = false,
                isBuffering = true
            )
        }
    }

    fun playPodcast(episode: PodcastEpisode) {
        val ctx = appContext ?: return
        startServiceIntent(ctx)
        stopSongMetadataPolling()

        val exo = player
        if (exo == null) {
            Handler(Looper.getMainLooper()).postDelayed({ playPodcast(episode) }, 400)
            return
        }

        val mediaUri = if (episode.isDownloaded && !episode.localFilePath.isNullOrEmpty() && File(episode.localFilePath).exists()) {
            Uri.fromFile(File(episode.localFilePath))
        } else {
            Uri.parse(episode.streamUrl)
        }

        val metadata = MediaMetadata.Builder()
            .setTitle(episode.programTitle)
            .setArtist(episode.episodeTitle)
            .setAlbumTitle("Mi Tierra Radio Podcast")
            .setDisplayTitle(episode.programTitle)
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(mediaUri)
            .setMediaMetadata(metadata)
            .build()

        exo.stop()
        exo.clearMediaItems()
        exo.setMediaItem(mediaItem)
        exo.prepare()
        exo.playWhenReady = true

        updateState {
            it.copy(
                isLiveRadio = false,
                activeEpisodeId = episode.id,
                currentTitle = episode.programTitle,
                currentSubtitle = episode.episodeTitle,
                nowPlayingSong = null,
                nowPlayingArtist = null,
                isError = false,
                isBuffering = true
            )
        }
    }

    fun togglePlayPause() {
        val exo = player
        if (exo == null) {
            playLiveRadio()
            return
        }

        if (exo.isPlaying) {
            exo.pause()
            updateState { it.copy(isPlaying = false) }
        } else {
            if (exo.mediaItemCount == 0) {
                playLiveRadio()
            } else {
                exo.play()
                updateState { it.copy(isPlaying = true) }
            }
        }
    }

    fun pause() {
        player?.pause()
        updateState { it.copy(isPlaying = false) }
    }

    fun resume() {
        player?.play()
        updateState { it.copy(isPlaying = true) }
    }

    fun stop() {
        stopSongMetadataPolling()
        player?.stop()
        updateState { it.copy(isPlaying = false, isBuffering = false) }
    }

    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
    }

    fun setVolume(vol: Float) {
        val clamped = vol.coerceIn(0f, 1f)
        _volume.value = clamped
        player?.volume = clamped
    }

    fun setSleepTimer(minutes: Int) {
        sleepTimerJob?.cancel()
        if (minutes <= 0) {
            updateState { it.copy(sleepTimerMinutesLeft = null) }
            return
        }

        updateState { it.copy(sleepTimerMinutesLeft = minutes) }

        sleepTimerJob = scope.launch {
            var remaining = minutes
            while (remaining > 0) {
                delay(60_000L)
                remaining--
                if (remaining > 0) {
                    updateState { it.copy(sleepTimerMinutesLeft = remaining) }
                }
            }
            pause()
            updateState { it.copy(sleepTimerMinutesLeft = null) }
        }
    }

    fun cancelSleepTimer() {
        sleepTimerJob?.cancel()
        sleepTimerJob = null
        updateState { it.copy(sleepTimerMinutesLeft = null) }
    }

    fun retryLiveStream() {
        val exo = player ?: return
        val metadata = MediaMetadata.Builder()
            .setTitle(RadioRepository.STATION_NAME)
            .setArtist("En Vivo • Santa Rosa de Osos")
            .setAlbumTitle(RadioRepository.STATION_SLOGAN)
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(RadioRepository.LIVE_STREAM_URL)
            .setMediaMetadata(metadata)
            .build()

        exo.clearMediaItems()
        exo.setMediaItem(mediaItem)
        exo.prepare()
        exo.playWhenReady = true
        updateState { it.copy(isError = false, isBuffering = true) }
    }

    private fun startProgressTracking() {
        stopProgressTracking()
        progressTrackingJob = scope.launch {
            while (true) {
                delay(800)
                val exo = player
                if (exo != null && exo.isPlaying && !_playbackState.value.isLiveRadio) {
                    updateState {
                        it.copy(
                            playbackPositionMs = exo.currentPosition.coerceAtLeast(0L),
                            durationMs = exo.duration.coerceAtLeast(0L)
                        )
                    }
                }
            }
        }
    }

    private fun stopProgressTracking() {
        progressTrackingJob?.cancel()
        progressTrackingJob = null
    }

    fun startSongMetadataPolling() {
        stopSongMetadataPolling()
        songMetadataJob = scope.launch {
            while (true) {
                if (_playbackState.value.isLiveRadio) {
                    val track = NowPlayingFetcher.fetchCurrentTrack()
                    if (track != null) {
                        updateState {
                            it.copy(
                                nowPlayingSong = track.song,
                                nowPlayingArtist = track.artist
                            )
                        }
                    }
                }
                delay(12_000L)
            }
        }
    }

    fun stopSongMetadataPolling() {
        songMetadataJob?.cancel()
        songMetadataJob = null
    }
}
