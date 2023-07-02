package ua.vitolex.buns.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import ua.vitolex.buns.presentation.screen.DescriptionScreen
import ua.vitolex.buns.presentation.screen.FavoritesScreen
import ua.vitolex.buns.presentation.screen.MScreen
import ua.vitolex.buns.presentation.screen.MainScreen
import ua.vitolex.buns.presentation.screen.MainViewModel
import ua.vitolex.buns.presentation.screen.SplashScreen
import kotlin.reflect.KFunction2

sealed class Screens(val rout: String) {
    object SplashScreen : Screens(rout = "splash_screen")
    object MainScreen : Screens(rout = "main_screen")
    object MScreen : Screens(rout = "m_screen")
    object DescriptionScreen : Screens(rout = "description_screen")
    object FavoritesScreen : Screens(rout = "favorites_screen")
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavHost(
    navController: NavHostController,
    selectedCategoryIndex: Int,
    vm: MainViewModel,
    saveArrayListToSharedPreferences: KFunction2<String, ArrayList<String>, Unit>,
    retrievedArrayList: ArrayList<String>,
    drawerState: DrawerState,
) {
    val springSpec = spring<IntOffset>(dampingRatio = 0.85f)
    val offset = 1100
    val durationEnter = 300
    val durationPopEnter = 150


    AnimatedNavHost(navController = navController, startDestination = Screens.SplashScreen.rout) {
        composable(
            route = Screens.SplashScreen.rout,
        ) {
            SplashScreen(navController = navController, viewModel = vm)
        }
        composable(
            route = Screens.MScreen.rout,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.SplashScreen.rout ->

                        fadeIn(animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))

                    else ->
                        slideInHorizontally(initialOffsetX = { it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
                }
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            },
        ) {
            MScreen(
                navController = navController,
                viewModel = vm,
                drawerState = drawerState
            )
        }

        composable(
            route = Screens.MainScreen.rout,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            }
//            enterTransition = {
//                slideInHorizontally(initialOffsetX = { it/2 },
//                    animationSpec = tween(500)
//                )
//            },
//            exitTransition = {
//                slideOutHorizontally(targetOffsetX = { -it/4 },
//                    animationSpec = tween(500, )
//                )
//            },
//            popEnterTransition = {
//                fadeIn(animationSpec = tween(50, easing = LinearEasing))
//
//            },
//            popExitTransition = {
//                fadeOut(animationSpec = tween(50, easing = LinearEasing))
//            },
        ) {
            MainScreen(
                navController = navController,
                viewModel = vm,
                selectedCategoryIndex = selectedCategoryIndex,
                drawerState = drawerState
            )
        }

        composable(route = Screens.DescriptionScreen.rout,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it/2 }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it/2 }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            }
        ) {
            DescriptionScreen(
                navController = navController,
                viewModel = vm,
                saveArrayListToSharedPreferences = saveArrayListToSharedPreferences,
                drawerState = drawerState
            )
        }
        composable(route = Screens.FavoritesScreen.rout,

            enterTransition = {
                when (initialState.destination.route) {
                    Screens.DescriptionScreen.rout ->
                        slideInVertically(
                            initialOffsetY = { -it/2 },
                            animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing)
                        )

                    else ->
                        slideInHorizontally(initialOffsetX = { it/2  }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
                }
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it/2  }, animationSpec = tween(durationEnter, easing = LinearOutSlowInEasing))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it/2  }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it/2  }, animationSpec = tween(durationPopEnter, easing = LinearOutSlowInEasing))
            }
        ) {
            FavoritesScreen(
                navController = navController,
                viewModel = vm,
                drawerState = drawerState
            )
        }
    }
}

