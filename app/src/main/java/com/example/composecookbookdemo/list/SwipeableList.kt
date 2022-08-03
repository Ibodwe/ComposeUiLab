package com.example.composecookbookdemo.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecookbookdemo.data.Album
import com.example.composecookbookdemo.data.AlbumsDataProvider
import kotlin.math.roundToInt

@Composable
fun SwipeableLists() {
    val albums by remember {
        mutableStateOf(AlbumsDataProvider.albums)
    }

    var remeberCurrentSelectedAlbumId by remember {
        mutableStateOf(-1)
    }

    LazyColumn {
        itemsIndexed(
            items = albums,
            itemContent = { index, album ->
                SwipeableListItem(index, album, remeberCurrentSelectedAlbumId) { selected  ->
                    remeberCurrentSelectedAlbumId = selected
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class)
@Composable
fun SwipeableListItem(index: Int, album: Album, currentSelectedId: Int, onItemSwiped: (Int) -> Unit) {
    val visible = remember(album.id) { mutableStateOf(true) }

    AnimatedVisibility(visible = visible.value) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            BackgroundListItem(modifier = Modifier.align(Alignment.CenterEnd))
            ForegroundListItem(album, index, index == currentSelectedId) {
                // visible.value = false
                onItemSwiped.invoke(index)
            }
        }
    }
}

enum class SwipeState {
    SWIPED, VISIBLE, MIDDLE
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForegroundListItem(album: Album, index: Int, isCurrentSelected: Boolean, onItemSwiped: (Int) -> Unit) {
    val swipeableState = androidx.compose.material.rememberSwipeableState(
        initialValue = SwipeState.VISIBLE,
        confirmStateChange = {
            if(it == SwipeState.MIDDLE) {
                onItemSwiped.invoke(index)
            }
            true
        }
    )
    LaunchedEffect(key1 = isCurrentSelected) {
        if(!isCurrentSelected) {
            swipeableState.animateTo(SwipeState.VISIBLE , tween(100))
        }
    }

    val swipeAnchors =
        mapOf(0f to SwipeState.VISIBLE, -1000f to SwipeState.SWIPED, -500f to SwipeState.MIDDLE)
    Row(
        modifier = Modifier
            .swipeable(
                state = swipeableState,
                anchors = swipeAnchors,
                thresholds = { _, _ -> FractionalThreshold(0.7f) },
                orientation = Orientation.Horizontal
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = album.song,
                style = typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${album.artist}, ${album.descriptions}",
                style = typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (album.id % 3 == 0) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .padding(4.dp)
                    .size(20.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun BackgroundListItem(modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.End, modifier = modifier) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null
            )
        }
    }
}