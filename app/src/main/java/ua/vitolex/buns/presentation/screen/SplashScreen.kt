package ua.vitolex.buns.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.ui.theme.OldGreen
import  ua.vitolex.buns.R
import ua.vitolex.buns.presentation.util.scaledSp


@Composable
fun SplashScreen(navController: NavHostController, viewModel: MainViewModel) {
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
            )
        )
        delay(1500L)
        navController.popBackStack()
        navController.navigate(Screens.MScreen.rout)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Бабусині рецепти",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 42.scaledSp(),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha.value),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Image(
            painter = painterResource(id = R.drawable.grandma12),
            contentDescription = "logo",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .alpha(alpha.value),
        )
        Spacer(modifier = Modifier.padding(24.dp))
    }
}