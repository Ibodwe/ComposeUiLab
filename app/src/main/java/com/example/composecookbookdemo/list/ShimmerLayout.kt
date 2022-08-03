package com.example.composecookbookdemo.list

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp

enum class ShimmerAnimationType {
    FADE, TRANSLATE, FADETRANSLATE, VERTICAL
}

@Composable
fun ShimmerLayout(){
    var shimmerAnimationType by remember { mutableStateOf(ShimmerAnimationType.FADE)}

    // 시작하자 마자 애니메이션이 실행, initialValue, targetValue가 정해져 있기 떄문
    val transition = rememberInfiniteTransition()

    val transitionAnim by transition.animateFloat(
        initialValue = 100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            tween(delayMillis = 500, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    val colorAnim by transition.animateColor(
        initialValue = Color.LightGray.copy(alpha = 0.6f),
        targetValue = Color.LightGray ,
        animationSpec = infiniteRepeatable(
            tween(delayMillis = 500, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    val list = if(shimmerAnimationType != ShimmerAnimationType.TRANSLATE) {
        listOf(colorAnim, colorAnim.copy(alpha = 0.5f))
    } else {
        listOf(Color.LightGray.copy(alpha = 0.6f), Color.LightGray)
    }

    val dpValue = if(shimmerAnimationType != ShimmerAnimationType.FADE) {
        transitionAnim.dp
    } else {
        2000.dp
    }

    @Composable
    fun buttonColors(type: ShimmerAnimationType) = ButtonDefaults.buttonColors(
        contentColor = if (shimmerAnimationType == type)
            Color.Blue else Color.LightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.FADE },
                colors = buttonColors(ShimmerAnimationType.FADE),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Fading")
            }
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.TRANSLATE },
                colors = buttonColors(ShimmerAnimationType.TRANSLATE),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Translating")
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.FADETRANSLATE },
                colors = buttonColors(ShimmerAnimationType.FADETRANSLATE),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Fade+Translate")
            }
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.VERTICAL },
                colors = buttonColors(ShimmerAnimationType.VERTICAL),
                modifier = Modifier
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Vertical")
            }
        }

        ShimmerItem(list, transitionAnim.dp.value, shimmerAnimationType == ShimmerAnimationType.VERTICAL)
        // ShimmerItemBig(list, dpValue.value, shimmerAnimationType == ShimmerAnimationType.VERTICAL)
        ShimmerItem(list, dpValue.value, shimmerAnimationType == ShimmerAnimationType.VERTICAL)
        ShimmerItem(list, dpValue.value, shimmerAnimationType == ShimmerAnimationType.VERTICAL)
    }
}

@Composable
fun ShimmerItem(
    lists: List<Color>, floatAnim: Float = 0f, isVertical: Boolean
) {
    val brush = if(isVertical) Brush.verticalGradient(lists, 0f , floatAnim) else {
        Brush.horizontalGradient(lists, 0f, floatAnim)
    }

    Row(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .size(100.dp)
                .background(brush = brush)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush = brush)
            )
        }
    }

}