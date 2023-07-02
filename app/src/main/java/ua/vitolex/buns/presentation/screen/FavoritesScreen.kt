package ua.vitolex.buns.presentation.screen


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.PopUpToBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.vitolex.buns.R
import ua.vitolex.buns.presentation.components.AppBar
import ua.vitolex.buns.presentation.components.BannerAdView
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.presentation.util.normalizedItemPosition
import ua.vitolex.buns.presentation.util.scaledSp
import ua.vitolex.buns.ui.theme.OldGreen
import ua.vitolex.buns.ui.theme.OldGrey
import kotlin.math.absoluteValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    drawerState: DrawerState,
) {
    val context = LocalContext.current
    val mainList = viewModel.favoriteRecipes.value

    val listState = rememberLazyListState()

    BackHandler(enabled = true, onBack = {
        viewModel.topBar = "Головна"
        navController.popBackStack(Screens.MScreen.rout, false)
        viewModel.selectCategoty(-1)
    })
    Scaffold(topBar = {
        AppBar(viewModel = viewModel, drawerState = drawerState, navController = navController)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 55.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 2.dp, end = 2.dp, top = 10.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 2.dp, end = 2.dp)
                    .weight(1f),
                state = listState
            ) {
                items(mainList, key = { it }) {
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                val value =
                                    1 - (listState.layoutInfo.normalizedItemPosition(it).absoluteValue * 0.015F)
                                scaleX = value
                                scaleY = value
                            }
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 0.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = OldGrey,
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    viewModel.selectItem(
                                        MainViewModel.ListItem(
                                            it,
                                            "",
                                        )
                                    )
                                    navController.navigate(
                                        route = Screens.DescriptionScreen.rout
                                    ) {
                                        navController.popBackStack(
                                            route = Screens.FavoritesScreen.rout,
                                            inclusive = false,
                                            saveState = false
                                        )
                                    }

                                }
                                .padding(12.dp)
                        ) {
                            Text(text = "~ ${it.substringBefore('.')} ~",
                                fontSize = 20.scaledSp(),
                                lineHeight = 20.scaledSp(),
                                color = Color.Black,)
                            Spacer(modifier = Modifier.height(5.dp))
                            val str =
                                context.assets.open("txt/${it}").bufferedReader().use {
                                    it.readText()
                                }
                            val strArray = str.split("|")
                            for (i in 1..strArray.size - 1) {
                                Text(
                                    text = strArray[i],
                                    fontSize = 16.scaledSp(),
                                    lineHeight = 16.scaledSp(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                if (strArray[i][strArray[i].length - 1].toString() == ".") {
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }

                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                BannerAdView(id = stringResource(id = R.string.main_banner2))
            }
        }
    }

}