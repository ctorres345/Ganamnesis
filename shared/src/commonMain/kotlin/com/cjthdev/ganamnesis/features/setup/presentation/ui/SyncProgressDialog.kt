package com.cjthdev.ganamnesis.features.setup.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cjthdev.ganamnesis.core.model.SyncStatus

@Composable
fun SyncProgressDialog(
    status: SyncStatus?,
    onDismiss: () -> Unit
) {
    if (status == null || !status.isSyncing) return

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(Color.White, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                CircularProgressIndicator(
                    progress = status.progress,
                    modifier = Modifier.size(80.dp),
                    strokeWidth = 8.dp,
                    color = Color(0xFF3E2723)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "${status.percentage}%",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = status.currentItem ?: "Syncing...",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}
