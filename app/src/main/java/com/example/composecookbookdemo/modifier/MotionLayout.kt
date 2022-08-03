package com.example.composecookbookdemo.modifier

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.MotionLayout

@Composable
fun MotionLayoutExample() {
    Column() {
        ButtonMotionExample()
    }
}

@Composable
fun ButtonMotionExample() {
    var animateButton by remember { mutableStateOf(false) }

    // buttonAnimation에 사용 될 progress
    val buttonAnimationProgress by animateFloatAsState(
        targetValue = if(animateButton) 1f else 0f,
        animationSpec = infiniteRepeatable(
            tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    MotionLayout(
       ConstraintSet(
            """ {
                button1: {
                width: "spread",
                height: 60,
                start: ['parent', 'start' , 16],
                end: ['parent', 'end', 16],
                top: ['parent', 'top', 16]
                },
                button2: {
                width: "spread",
                height: 60,
                start: ['parent','start', 16],
                end: ['parent', 'end', 16],
                top: ['button1', 'bottom', 16]
                },
                button3: {
                width: "spread",
                height: 60,
                start: ['parent', 'start', 16],
                end: ['parent', 'end', 16],
                top: ['button2', 'bottom', 16]
                }
                } """
        ),
        ConstraintSet(
            """ {
                button1: {
                width: 100,
                height: 350,
                start: ['parent', 'start' , 16],
                end: ['button2', 'start', 16],
                top: ['parent', 'top', 16]
                },
                button2: {
                width: 100,
                height: 350,
                start: ['button1','start', 16],
                end: ['button3', 'end', 16],
                top: ['parent', 'top', 16],
                },
                button3: {
                width: 100,
                height: 350,
                start: ['button2', 'end', 16],
                end: ['parent', 'end', 16],
                top: ['parent', 'top', 16],
                }
                } """
        ),
        progress = buttonAnimationProgress,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Button(
            onClick = { animateButton = !animateButton },
            modifier = Modifier.layoutId("button1")
        ) {
            Text(text = "Button1")
        }
        Button(
            onClick = { animateButton = !animateButton },
            modifier = Modifier.layoutId("button2")
        ) {
            Text(text = "Button2")
        }
        Button(
            onClick = { animateButton = !animateButton },
            modifier = Modifier.layoutId("button3")
        ) {
            Text(text = "Button3")
        }
    }
}