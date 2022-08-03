package com.example.composecookbookdemo.list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.composecookbookdemo.component.YoutubeChip


enum class AnimationType {
    FADE, SCALE, SLIDE, FADE_SLIDE, SLIDE_UP, ROTATE_X
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedLists() {
    val tweets = listOf<String>()
    var currentAnimationType by remember { mutableStateOf(AnimationType.FADE) }
    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(12.dp)
        ) {
            items(AnimationType.values()) {
                YoutubeChip(
                    selected = it == currentAnimationType,
                    text = currentAnimationType.name,
                    modifier = Modifier.clickable { currentAnimationType = it })
            }
        }
    }
    LazyColumn {
        itemsIndexed(
            items = tweets,
            itemContent = { index, tweet ->
                AnimatedListItem(
                    item = tweets,
                    currentSelectedType = currentAnimationType
                )
            })
    }
}

@Composable
fun AnimatedListItem(
    item: List<String>,
    currentSelectedType: AnimationType
) {
    val animatedModifier = when (currentSelectedType) {
        AnimationType.FADE -> {
            val animatedProgress = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(450)
                )
            }
            Modifier
                .padding(8.dp)
                .alpha(animatedProgress.value)
        }
        AnimationType.SCALE -> {
            val animatedProgress = remember { Animatable(initialValue = 0.8f) }
            LaunchedEffect(key1 = Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(350)
                )
            }
            Modifier
                .padding(8.dp)
                .scale(animatedProgress.value, animatedProgress.value)
        }
        else -> {
            Modifier
                .padding(8.dp)
        }
    }

    Row(
        modifier = animatedModifier
    ) {

    }

}