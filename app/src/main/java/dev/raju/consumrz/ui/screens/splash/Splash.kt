package dev.raju.consumrz.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.navigation.NavRoute

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

object SplashRoute : NavRoute<SplashViewModel> {

    override val route = "splash"

    @Composable
    override fun viewModel(): SplashViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SplashViewModel) = SplashScreen(viewModel)
}

@Composable
fun SplashScreen(
    viewModel: SplashViewModel
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_lego))
    val progress by animateLottieCompositionAsState(composition, iterations = Integer.MAX_VALUE)

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(key1 = Unit) {
            viewModel.checkLogin()
        }
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}