package com.example.util

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import com.example.model.DayType
import com.example.model.ProgramItem
import java.util.Calendar

object CalendarHelper {

    fun addProgramToCalendar(context: Context, program: ProgramItem) {
        try {
            val calendar = Calendar.getInstance()

            // Calculate the target day
            val todayDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val targetDayOfWeek = when (program.dayType) {
                DayType.SATURDAY -> Calendar.SATURDAY
                DayType.SUNDAY -> Calendar.SUNDAY
                DayType.WEEKDAY -> {
                    // If today is weekend, jump to Monday
                    if (todayDayOfWeek == Calendar.SATURDAY || todayDayOfWeek == Calendar.SUNDAY) {
                        Calendar.MONDAY
                    } else {
                        todayDayOfWeek
                    }
                }
                DayType.HOLIDAY -> todayDayOfWeek
            }

            while (calendar.get(Calendar.DAY_OF_WEEK) != targetDayOfWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            // Set start time
            calendar.set(Calendar.HOUR_OF_DAY, program.startHour)
            calendar.set(Calendar.MINUTE, program.startMinute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val startTimeMillis = calendar.timeInMillis

            // Set end time
            val endCalendar = Calendar.getInstance().apply {
                timeInMillis = startTimeMillis
                set(Calendar.HOUR_OF_DAY, program.endHour)
                set(Calendar.MINUTE, program.endMinute)
                if (program.endHour < program.startHour) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            val endTimeMillis = endCalendar.timeInMillis

            val rrule = when (program.dayType) {
                DayType.WEEKDAY -> "FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR"
                DayType.SATURDAY -> "FREQ=WEEKLY;BYDAY=SA"
                DayType.SUNDAY -> "FREQ=WEEKLY;BYDAY=SU"
                DayType.HOLIDAY -> "FREQ=MONTHLY"
            }

            val hostText = if (!program.host.isNullOrEmpty()) "Conduce: ${program.host}\n" else ""
            val description = "$hostText${program.description}\n\nSintoniza en vivo 88.1 FM o en la app de Mi Tierra Radio. Santa Rosa de Osos."

            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, "${program.title} | Mi Tierra Radio 88.1 FM")
                putExtra(CalendarContract.Events.DESCRIPTION, description)
                putExtra(CalendarContract.Events.EVENT_LOCATION, "88.1 FM - Santa Rosa de Osos")
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeMillis)
                putExtra(CalendarContract.Events.RRULE, rrule)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No se pudo abrir la aplicación de calendario", Toast.LENGTH_SHORT).show()
        }
    }
}
