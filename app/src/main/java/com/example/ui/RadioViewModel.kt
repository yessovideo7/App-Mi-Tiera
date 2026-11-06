package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RadioRepository
import com.example.download.PodcastDownloadManager
import com.example.model.DayType
import com.example.model.PlaybackState
import com.example.model.PodcastEpisode
import com.example.model.ProgramCategory
import com.example.model.ProgramItem
import com.example.playback.RadioPlayerController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RadioViewModel(application: Application) : AndroidViewModel(application) {

    private val downloadManager = PodcastDownloadManager(application)

    val playbackState: StateFlow<PlaybackState> = RadioPlayerController.playbackState
    val volume: StateFlow<Float> = RadioPlayerController.volume
    val episodes: StateFlow<List<PodcastEpisode>> = downloadManager.episodes

    private val _selectedDayType = MutableStateFlow(RadioRepository.getCurrentDayType())
    val selectedDayType: StateFlow<DayType> = _selectedDayType.asStateFlow()

    private val _selectedCategory = MutableStateFlow<ProgramCategory?>(null)
    val selectedCategory: StateFlow<ProgramCategory?> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentLiveProgram = MutableStateFlow<ProgramItem?>(null)
    val currentLiveProgram: StateFlow<ProgramItem?> = _currentLiveProgram.asStateFlow()

    private val _nextLiveProgram = MutableStateFlow<ProgramItem?>(null)
    val nextLiveProgram: StateFlow<ProgramItem?> = _nextLiveProgram.asStateFlow()

    private val _favoriteReminders = MutableStateFlow<Set<String>>(emptySet())
    val favoriteReminders: StateFlow<Set<String>> = _favoriteReminders.asStateFlow()

    // Filtered programs for schedule screen
    val displayedPrograms: StateFlow<List<ProgramItem>> = combine(
        _selectedDayType,
        _selectedCategory,
        _searchQuery
    ) { day, category, query ->
        var list = RadioRepository.getProgramsForDay(day)
        if (category != null) {
            list = list.filter { it.category == category }
        }
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                        it.description.lowercase().contains(q) ||
                        (it.host?.lowercase()?.contains(q) == true)
            }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        RadioPlayerController.initService(application)
        updateCurrentOnAir()

        // Periodically refresh current on-air program
        viewModelScope.launch {
            while (true) {
                delay(30_000L)
                updateCurrentOnAir()
            }
        }
    }

    private fun updateCurrentOnAir() {
        val (current, next) = RadioRepository.getCurrentLiveProgram()
        _currentLiveProgram.value = current
        _nextLiveProgram.value = next
    }

    fun playLiveRadio() {
        RadioPlayerController.playLiveRadio()
    }

    fun playPodcast(episode: PodcastEpisode) {
        RadioPlayerController.playPodcast(episode)
    }

    fun togglePlayPause() {
        RadioPlayerController.togglePlayPause()
    }

    fun setVolume(vol: Float) {
        RadioPlayerController.setVolume(vol)
    }

    fun setSleepTimer(minutes: Int) {
        RadioPlayerController.setSleepTimer(minutes)
    }

    fun cancelSleepTimer() {
        RadioPlayerController.cancelSleepTimer()
    }

    fun selectDayType(dayType: DayType) {
        _selectedDayType.value = dayType
    }

    fun selectCategory(category: ProgramCategory?) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleReminder(programId: String) {
        val current = _favoriteReminders.value
        _favoriteReminders.value = if (current.contains(programId)) {
            current - programId
        } else {
            current + programId
        }
    }

    fun startDownload(episodeId: String) {
        downloadManager.startDownload(episodeId)
    }

    fun deleteDownload(episodeId: String) {
        downloadManager.deleteDownload(episodeId)
    }
}
