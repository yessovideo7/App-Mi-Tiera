package com.example.model

data class PlaybackState(
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isLiveRadio: Boolean = true,
    val currentTitle: String = "Mi Tierra Radio 88.1 FM",
    val currentSubtitle: String = "¡Cómo me gusta! • Santa Rosa de Osos",
    val nowPlayingSong: String? = null,
    val nowPlayingArtist: String? = null,
    val activeEpisodeId: String? = null,
    val playbackPositionMs: Long = 0L,
    val durationMs: Long = 0L,
    val sleepTimerMinutesLeft: Int? = null
) {
    val displayTrackInfo: String?
        get() = when {
            !nowPlayingSong.isNullOrBlank() && !nowPlayingArtist.isNullOrBlank() ->
                "$nowPlayingSong • $nowPlayingArtist"
            !nowPlayingSong.isNullOrBlank() -> nowPlayingSong
            else -> null
        }
}
