package com.cjthdev.ganamnesis.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun GanamnesisAvatar(
    avatarUrl: String?,
    fallbackText: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
) {
    if (avatarUrl != null) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Profile picture",
            modifier =
                modifier
                    .size(size)
                    .clip(CircleShape),
        )
    } else {
        Box(
            modifier =
                modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(Color(0xFF3E2723)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = fallbackText.take(1).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value / 2.8).sp,
            )
        }
    }
}
