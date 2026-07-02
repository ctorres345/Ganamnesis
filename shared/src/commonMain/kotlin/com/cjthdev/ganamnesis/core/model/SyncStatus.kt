package com.cjthdev.ganamnesis.core.model

data class SyncStatus(
    val isSyncing: Boolean = false,
    val progress: Float = 0f,
    val currentItem: String? = null,
    val totalItems: Int = 0,
    val completedItems: Int = 0,
    val error: String? = null
) {
    val percentage: Int
        get() = if (totalItems > 0) ((completedItems.toFloat() / totalItems) * 100).toInt() else 0
}
