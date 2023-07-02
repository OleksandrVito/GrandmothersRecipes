package ua.vitolex.buns.presentation.util

import androidx.compose.foundation.lazy.LazyListLayoutInfo

fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size * 2) / 2F
            (it.offset.toFloat() - center) / center
        }
        ?: 0F