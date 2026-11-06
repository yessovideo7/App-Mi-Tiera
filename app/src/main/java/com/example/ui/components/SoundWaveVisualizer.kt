package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BrandGreenGlow
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandVibrantGreen

@Composable
fun SoundWaveVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 12,
    maxHeight: Dp = 48.dp,
    barWidth: Dp = 4.dp,
    spacing: Dp = 3.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave_transition")

    // Heights animating with slightly offset frequencies
    val animations = (0 until barCount).map { index ->
        val duration = 380 + (index * 65) % 450
        val target = if (isPlaying) 0.95f else 0.15f
        val animatedHeight by infiniteTransition.animateFloat(
            initialValue = 0.15f,
            targetValue = if (isPlaying) ((index * 7 + 13) % 10) / 10f * 0.7f + 0.3f else 0.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
        animatedHeight
    }

    Row(
        modifier = modifier.height(maxHeight),
        horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        animations.forEach { factor ->
            val barHeight = maxHeight * factor
            Box(
                modifier = Modifier
                    .width(barWidth)
                    .height(barHeight)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(BrandGreenGlow, BrandGreenLight, BrandVibrantGreen)
                        ),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}
