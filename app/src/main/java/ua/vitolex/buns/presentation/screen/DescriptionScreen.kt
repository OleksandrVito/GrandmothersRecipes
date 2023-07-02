package ua.vitolex.buns.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.vitolex.buns.R
import ua.vitolex.buns.presentation.components.AppBar
import ua.vitolex.buns.presentation.components.BannerAdView
import ua.vitolex.buns.presentation.components.BlockCanvas
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.presentation.util.scaledSp
import ua.vitolex.buns.ui.theme.OldGreen
import ua.vitolex.buns.ui.theme.OldGrey
import ua.vitolex.buns.ui.theme.OldOrange
import kotlin.reflect.KFunction2


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    saveArrayListToSharedPreferences: KFunction2<String, ArrayList<String>, Unit>,
    drawerState: DrawerState
) {
    val currentItem = viewModel.selectedItem
    val favoriteRecipes = viewModel.favoriteRecipes

    val context = LocalContext.current
    val str = context.assets.open("txt/${currentItem.value.title}").bufferedReader().use {
        it.readText()
    }
    val strArray = str.split("|")
    Scaffold(
        topBar = { AppBar(viewModel = viewModel, drawerState = drawerState, navController = navController)}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 55.dp)
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(10.dp)
        ) {
            Box(contentAlignment = Alignment.TopCenter) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(OldGrey)
                        .padding(bottom = 6.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(OldGrey)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(OldGrey)
                    )
                    BlockCanvas()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .padding(bottom = 0.dp, top = 5.dp)
                    ) {
                        Text(
                            text = strArray[0].substringBefore('.'),
                            fontSize = 22.scaledSp(),
                            lineHeight = 22.scaledSp(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(end = 50.dp, bottom = 10.dp)
                                .fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 74.dp, top = 8.dp, bottom = 8.dp),
                            thickness = 2.dp,
                            color = Color.Black
                        )
                        LazyColumn() {
                            for (i in 1..strArray.size - 1) {
                                item {
                                    Text(
                                        text = strArray[i],
                                        fontSize = 18.scaledSp(),
                                        lineHeight = 18.scaledSp(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (strArray[i][strArray[i].length - 1].toString() == ":") {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Normal
                                        },
                                        color = Color.Black,
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
                        .padding(top = 15.dp, end = 10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (currentItem.value.title in favoriteRecipes.value) OldOrange else Color.Gray,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable {
                                viewModel.selectFavorite(currentItem.value.title)
                                val myArrayList: ArrayList<String> =
                                    ArrayList(viewModel.favoriteRecipes.value)

                                // Save the ArrayList to SharedPreferences
                                saveArrayListToSharedPreferences("myArrayList", myArrayList)
                                if (viewModel.topBar == "Улюблені") {
                                    navController.navigate(Screens.FavoritesScreen.rout) { navController.popBackStack() }
                                }
                            }
                    )
                }
            }
        }
    }

}
