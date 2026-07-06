package com.cjthdev.ganamnesis.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cjthdev.ganamnesis.core.ui.theme.GrayText
import com.cjthdev.ganamnesis.core.ui.theme.PrimaryDark

@Composable
fun WizardProgressIndicator(
    currentStep: Int,
    totalSteps: Int = 3,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = { currentStep / totalSteps.toFloat() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
            color = PrimaryDark,
            trackColor = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Step $currentStep of $totalSteps",
            fontSize = 12.sp,
            color = GrayText,
        )
    }
}
