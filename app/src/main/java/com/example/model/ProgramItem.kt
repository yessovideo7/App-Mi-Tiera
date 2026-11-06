package com.example.model

data class ProgramItem(
    val id: String,
    val title: String,
    val scheduleDisplay: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val dayType: DayType,
    val host: String? = null,
    val description: String,
    val category: ProgramCategory,
    val badge: String? = null
) {
    /**
     * Checks if given minute of day (0..1439) falls into this program.
     */
    fun isOnAir(minuteOfDay: Int): Boolean {
        val start = startHour * 60 + startMinute
        val end = endHour * 60 + endMinute
        return if (end > start) {
            minuteOfDay in start until end
        } else {
            // Program crosses midnight (e.g., 23:00 to 03:00)
            minuteOfDay >= start || minuteOfDay < end
        }
    }

    /**
     * Calculates progress fraction between 0.0f and 1.0f if program is active.
     */
    fun getProgressFraction(minuteOfDay: Int): Float {
        val start = startHour * 60 + startMinute
        val end = endHour * 60 + endMinute
        val duration = if (end > start) end - start else (1440 - start) + end
        if (duration <= 0) return 0f

        val elapsed = if (end > start) {
            minuteOfDay - start
        } else {
            if (minuteOfDay >= start) minuteOfDay - start else (1440 - start) + minuteOfDay
        }
        return (elapsed.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
    }
}
