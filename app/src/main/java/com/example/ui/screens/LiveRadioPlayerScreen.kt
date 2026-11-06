package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Radio
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.RadioRepository
import com.example.ui.RadioViewModel
import com.example.ui.components.LiveBadge
import com.example.ui.components.SleepTimerBottomSheet
import com.example.ui.components.SoundWaveVisualizer
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.BrandBlueAccent
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBlueNavy
import com.example.ui.theme.BrandDeepBlue
import com.example.ui.theme.BrandDeepBlueDark
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGreenGlow
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandRedLight
import com.example.ui.theme.BrandVibrantGreen
import com.example.ui.theme.BrandVibrantRed
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardElevated
import com.example.ui.theme.SurfaceNavyDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.CalendarHelper
import com.example.util.ShareAndContactHelper
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveRadioPlayerScreen(
    viewModel: RadioViewModel,
    onNavigateToSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val playbackState by viewModel.playbackState.collectAsState()
    val volume by viewModel.volume.collectAsState()
    val currentShow by viewModel.currentLiveProgram.collectAsState()
    val nextShow by viewModel.nextLiveProgram.collectAsState()

    var showSleepTimerSheet by remember { mutableStateOf(false) }
    var showFullPosterDialog by remember { mutableStateOf(false) }

    val playButtonScale by animateFloatAsState(
        targetValue = if (playbackState.isPlaying) 1.05f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "play_btn_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BrandDeepBlueDark, BrandDeepBlue, SurfaceNavyDark)
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top station frequency bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_station_logo),
                    contentDescription = "Logo Mi Tierra Radio",
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .background(BrandBlueNavy, RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "88.1 FM",
                            color = BrandGold,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = "Santa Rosa de Osos",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showSleepTimerSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.Bedtime,
                        contentDescription = "Temporizador",
                        tint = if (playbackState.sleepTimerMinutesLeft != null) BrandGreenLight else TextMuted
                    )
                }
                IconButton(onClick = { ShareAndContactHelper.shareApp(context, currentShow?.title) }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        tint = TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Hero Cover Artwork Card with Official Team Photo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(20.dp))
                .clickable { showFullPosterDialog = true },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_equipo_locutores),
                    contentDescription = "Equipo oficial de locutores de Mi Tierra Radio",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient scrim overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    BrandDeepBlueDark.copy(alpha = 0.5f),
                                    BrandDeepBlueDark.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Overlay station branding and live badge
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        LiveBadge(isLive = playbackState.isLiveRadio)

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BrandDeepBlueDark.copy(alpha = 0.75f)
                        ) {
                            Text(
                                text = "¡CÓMO ME GUSTA!",
                                color = BrandGreenGlow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = RadioRepository.STATION_NAME,
                            color = TextPrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Emisora de Interés Público • Alcaldía de Santa Rosa de Osos",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Currently On-Air Show Card with Progress
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Radio,
                            contentDescription = "Al aire",
                            tint = BrandGreenLight,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AL AIRE AHORA",
                            color = BrandGreenLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    currentShow?.scheduleDisplay?.let { schedule ->
                        Text(
                            text = schedule,
                            color = AccentGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = currentShow?.title ?: playbackState.currentTitle,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                currentShow?.host?.let { host ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Conduce: $host",
                        color = BrandBlueLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                currentShow?.description?.let { desc ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = desc,
                        color = TextMuted,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Show progress bar
                currentShow?.let { show ->
                    val calendar = Calendar.getInstance()
                    val minuteOfDay = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
                    val progress = show.getProgressFraction(minuteOfDay)

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = BrandGreenLight,
                        trackColor = SurfaceCardElevated
                    )
                }

                // Action to view Parrilla or add to calendar
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nextShow?.let { "Próximo: ${it.title}" } ?: "Parrilla completa",
                        color = TextMuted,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Row {
                        currentShow?.let { show ->
                            IconButton(
                                onClick = { CalendarHelper.addProgramToCalendar(context, show) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Añadir al calendario",
                                    tint = BrandGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = "Ver Parrilla",
                            color = BrandGreenLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { onNavigateToSchedule() }
                                .padding(vertical = 4.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Canción en Reproducción (Now Playing Track)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (!playbackState.nowPlayingSong.isNullOrBlank()) BrandGreenLight.copy(alpha = 0.6f) else BorderSubtle,
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (!playbackState.nowPlayingSong.isNullOrBlank()) SurfaceCardElevated else SurfaceCard
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            if (!playbackState.nowPlayingSong.isNullOrBlank()) BrandVibrantGreen.copy(alpha = 0.2f) else BrandBlueNavy.copy(alpha = 0.5f),
                            CircleShape
                        )
                        .border(
                            1.dp,
                            if (!playbackState.nowPlayingSong.isNullOrBlank()) BrandGreenLight else BrandBlueLight.copy(alpha = 0.5f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Canción",
                        tint = if (!playbackState.nowPlayingSong.isNullOrBlank()) BrandGreenLight else TextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (!playbackState.nowPlayingSong.isNullOrBlank()) "CANCIÓN EN REPRODUCCIÓN" else "MÚSICA EN VIVO",
                            color = if (!playbackState.nowPlayingSong.isNullOrBlank()) BrandGreenLight else TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.1.sp
                        )
                        if (!playbackState.nowPlayingSong.isNullOrBlank()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(BrandGreenGlow, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = playbackState.nowPlayingSong ?: "Mi Tierra Radio 88.1 FM",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!playbackState.nowPlayingArtist.isNullOrBlank()) {
                        Text(
                            text = playbackState.nowPlayingArtist ?: "",
                            color = AccentGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else if (playbackState.nowPlayingSong.isNullOrBlank()) {
                        Text(
                            text = "¡Cómo me gusta! • Santa Rosa de Osos",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                if (!playbackState.nowPlayingSong.isNullOrBlank()) {
                    IconButton(
                        onClick = {
                            val songMsg = "¡Estoy escuchando '${playbackState.nowPlayingSong}${if (!playbackState.nowPlayingArtist.isNullOrBlank()) " de " + playbackState.nowPlayingArtist else ""}' en Mi Tierra Radio 88.1 FM (Santa Rosa de Osos)! Sintonízala en: ${RadioRepository.LIVE_STREAM_URL}"
                            val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(android.content.Intent.EXTRA_TEXT, songMsg)
                            }
                            context.startActivity(android.content.Intent.createChooser(intent, "Compartir Canción"))
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir canción",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Sound Wave Visualizer
        SoundWaveVisualizer(
            isPlaying = playbackState.isPlaying,
            barCount = 14,
            maxHeight = 44.dp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error message notification
        AnimatedVisibility(visible = playbackState.isError) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(BrandVibrantRed.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = playbackState.errorMessage ?: "Reconectando con la emisora...",
                    color = BrandRedLight,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.playLiveRadio() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reintentar", tint = BrandRedLight)
                }
            }
        }

        // Central Floating Big Play / Pause / Buffering Button
        Box(
            modifier = Modifier
                .scale(playButtonScale)
                .size(92.dp)
                .shadow(16.dp, CircleShape, spotColor = BrandGreenGlow)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(BrandGreenLight, BrandVibrantGreen, BrandBlueNavy)
                    ),
                    shape = CircleShape
                )
                .clickable { viewModel.togglePlayPause() },
            contentAlignment = Alignment.Center
        ) {
            if (playbackState.isBuffering) {
                CircularProgressIndicator(
                    modifier = Modifier.size(46.dp),
                    color = TextPrimary,
                    strokeWidth = 3.5.dp
                )
            } else {
                Icon(
                    imageVector = if (playbackState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (playbackState.isPlaying) "Pausar" else "Reproducir",
                    tint = TextPrimary,
                    modifier = Modifier.size(52.dp)
                )
            }
        }

        Text(
            text = if (playbackState.isBuffering) "Conectando con la señal..."
            else if (playbackState.isPlaying) "Reproduciendo en directo"
            else "Toca para sintonizar",
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Volume Control Slider
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(SurfaceCard, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.VolumeDown,
                contentDescription = "Volumen bajo",
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Slider(
                value = volume,
                onValueChange = { viewModel.setVolume(it) },
                valueRange = 0f..1f,
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = BrandGreenLight,
                    activeTrackColor = BrandGreenLight,
                    inactiveTrackColor = SurfaceCardElevated
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Volumen alto",
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Action Buttons: WhatsApp a Cabina & Compartir
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    ShareAndContactHelper.openWhatsAppCabina(context, currentShow?.title)
                },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandVibrantGreen,
                    contentColor = TextPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "WhatsApp Cabina",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            FilledTonalButton(
                onClick = {
                    ShareAndContactHelper.shareApp(context, currentShow?.title)
                },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = SurfaceCardElevated,
                    contentColor = TextPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = BrandBlueLight
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Compartir App",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Institutional Badge Footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BrandDeepBlueDark.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                .border(1.dp, BorderSubtle.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Alcaldía de Santa Rosa de Osos • Renace la Esperanza",
                    color = AccentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Emisora Comunitaria y de Interés Público • 88.1 FM",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (showSleepTimerSheet) {
        SleepTimerBottomSheet(
            currentMinutesLeft = playbackState.sleepTimerMinutesLeft,
            onSelectMinutes = { viewModel.setSleepTimer(it) },
            onCancelTimer = { viewModel.cancelSleepTimer() },
            onDismiss = { showSleepTimerSheet = false }
        )
    }

    if (showFullPosterDialog) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showFullPosterDialog = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_portada_oficial),
                            contentDescription = "Portada oficial Mi Tierra Radio",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { showFullPosterDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandVibrantGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cerrar Portada", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
