package com.example.playback

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NowPlayingFetcher {

    private const val METADATA_URL = "https://play14.tikast.com:20134/currentsong?sid=1"

    data class Track(
        val song: String,
        val artist: String?,
        val raw: String
    )

    suspend fun fetchCurrentTrack(): Track? = withContext(Dispatchers.IO) {
        try {
            val url = URL(METADATA_URL)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 4500
            conn.readTimeout = 4500
            conn.requestMethod = "GET"
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 MiTierraRadio")
            conn.connect()

            if (conn.responseCode in 200..299) {
                val reader = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"))
                val raw = reader.readLine()?.trim()
                reader.close()
                conn.disconnect()

                if (!raw.isNullOrBlank()) {
                    return@withContext parseRawTrack(raw)
                }
            }
        } catch (e: Exception) {
            // Silently ignore intermittent radio server latency
        }
        null
    }

    fun parseRawTrack(raw: String): Track {
        val trimmed = raw.trim()
        val separators = listOf(" - ", " – ", " — ")
        for (sep in separators) {
            if (trimmed.contains(sep)) {
                val parts = trimmed.split(sep, limit = 2)
                val first = parts[0].trim()
                val second = parts[1].trim()
                return Track(song = first, artist = second, raw = trimmed)
            }
        }
        return Track(song = trimmed, artist = null, raw = trimmed)
    }
}
