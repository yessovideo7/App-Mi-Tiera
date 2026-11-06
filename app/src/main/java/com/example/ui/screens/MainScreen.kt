package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.RadioViewModel
import com.example.ui.components.MiniPlayerBar
import com.example.ui.theme.BrandDeepBlueDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandVibrantGreen
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavyDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class RadioTab(val title: String) {
    LIVE("En Vivo"),
    SCHEDULE("Parrilla"),
    DOWNLOADS("Descargas"),
    STATION("Emisora")
}

@Composable
fun MainScreen(
    viewModel: RadioViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val playbackState by viewModel.playbackState.collectAsState()

    // Notification permission launcher for Android 13+
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* Handled */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Handle system back button to return to home / live player
    BackHandler(enabled = selectedTab != 0) {
        selectedTab = 0
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BrandDeepBlueDark,
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(SurfaceNavyDark)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // Persistent mini player when user is on another screen
                if (selectedTab != 0 && (playbackState.isPlaying || playbackState.isBuffering)) {
                    MiniPlayerBar(
                        playbackState = playbackState,
                        onTogglePlay = { viewModel.togglePlayPause() },
                        onClickBar = { selectedTab = 0 }
                    )
                }

                NavigationBar(
                    containerColor = SurfaceNavyDark,
                    tonalElevation = 8.dp
                ) {
                    val items = listOf(
                        Triple(RadioTab.LIVE, Icons.Filled.Radio, Icons.Outlined.Radio),
                        Triple(RadioTab.SCHEDULE, Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
                        Triple(RadioTab.DOWNLOADS, Icons.Filled.Download, Icons.Outlined.Download),
                        Triple(RadioTab.STATION, Icons.Filled.Info, Icons.Outlined.Info)
                    )

                    items.forEachIndexed { index, item ->
                        val isSelected = selectedTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = index },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.second else item.third,
                                    contentDescription = item.first.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.first.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BrandDeepBlueDark,
                                selectedTextColor = BrandGreenLight,
                                indicatorColor = BrandGreenLight,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> LiveRadioPlayerScreen(
                    viewModel = viewModel,
                    onNavigateToSchedule = { selectedTab = 1 }
                )
                1 -> ScheduleScreen(
                    viewModel = viewModel,
                    onPlayLiveRequested = { selectedTab = 0 }
                )
                2 -> DownloadsScreen(
                    viewModel = viewModel
                )
                3 -> StationInfoScreen()
            }
        }
    }
}
