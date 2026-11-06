package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.RadioRepository
import com.example.model.DayType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Mi Tierra 88.1 FM", appName)
  }

  @Test
  fun `verify weekday schedule loaded`() {
    val programs = RadioRepository.getProgramsForDay(DayType.WEEKDAY)
    assertTrue(programs.isNotEmpty())
    val ecos = programs.find { it.title.contains("Ecos del Territorio") }
    assertTrue(ecos != null)
  }
}
