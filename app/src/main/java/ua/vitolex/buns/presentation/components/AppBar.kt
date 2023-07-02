package ua.vitolex.buns.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.presentation.screen.MainViewModel
import ua.vitolex.buns.presentation.util.scaledSp
import ua.vitolex.buns.ui.theme.OldGreen
import java.util.Timer
import kotlin.concurrent.schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar (viewModel: MainViewModel, drawerState: DrawerState, navController: NavHostController,){
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = viewModel.topBar,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = 5.dp,
                    end = 5.dp
                ),
                overflow = TextOverflow.Ellipsis,
                fontSize = 30.scaledSp(), fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { scope.launch { drawerState.open() } }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer",
                    tint =  Color.Black,
                )
            }
        },
        actions = {
                IconButton(
                    onClick = {
                        navController.navigate(Screens.FavoritesScreen.rout)
                        viewModel.topBar = "Улюблені"
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint =  Color.Black,)
                }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}