package com.example.model

data class PodcastEpisode(
    val id: String,
    val programTitle: String,
    val episodeTitle: String,
    val description: String,
    val durationText: String,
    val dateText: String,
    val streamUrl: String,
    val isDownloaded: Boolean = false,
    val isDownloading: Boolean = false,
    val downloadProgress: Float = 0f,
    val localFilePath: String? = null,
    val fileSizeBytes: Long = 0L,
    val fileSizeFormatted: String = ""
)
