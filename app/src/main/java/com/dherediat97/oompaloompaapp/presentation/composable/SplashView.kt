package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dherediat97.oompaloompaapp.R
import kotlinx.coroutines.delay


@Composable
fun SplashView(modifier: Modifier, isAnimationFinished: () -> Unit) {
    val composition by
    rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chocolate_loading))

    //State of the animation
    val loadingComposition by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 2.5F
    )

    LaunchedEffect(Unit) {
        delay(2200)
        isAnimationFinished()
    }

    LottieAnimation(
        composition = composition,
        progress = { loadingComposition },
        modifier = modifier
    )
}