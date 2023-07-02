package ua.vitolex.buns.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.vitolex.buns.R
import ua.vitolex.buns.presentation.components.AppBar
import ua.vitolex.buns.presentation.components.BannerAdView
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.presentation.util.scaledSp
import ua.vitolex.buns.ui.theme.OldGrey
import ua.vitolex.buns.ui.theme.OldOrange

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    drawerState: DrawerState,
) {
    val context = LocalContext.current
    val categorys = stringArrayResource(id = R.array.drawer_list)

    Scaffold(
        topBar = {
            AppBar(
                viewModel = viewModel,
                drawerState = drawerState,
                navController = navController
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 60.dp)

        ) {

            categorys.forEachIndexed() { index, element ->
                item {
                    val arrayDescription = viewModel.getDescriptionCategory(index, context)
                    var smallDescription by remember { mutableStateOf(true) }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(OldGrey)

                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth().height(28.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_trillium2),
                                    contentDescription = "icon",
                                    tint = OldOrange,
                                    modifier = Modifier
                                        .padding(top = 6.dp),
                                )
                                Text(
                                    text = element,
                                    fontSize = 24.scaledSp(),
                                    lineHeight = 24.scaledSp(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(start = 5.dp, bottom = 0.dp, end = 5.dp),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_trillium2),
                                    contentDescription = "icon",
                                    tint = OldOrange,
                                    modifier = Modifier
                                        .padding(top = 6.dp),
                                )
                            }

                            Text(
                                buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = OldOrange,
                                            fontSize = 22.scaledSp(),
                                            FontWeight(900)
                                        )
                                    ) {
                                        append(arrayDescription[0])
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Black,
                                            fontSize = 20.scaledSp(),
                                        )
                                    ) {
                                        append(arrayDescription[1])
                                    }
                                },
                                lineHeight = 20.scaledSp(),
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (!smallDescription) {
                                            smallDescription = !smallDescription
                                        }
                                    }
                                    .padding(bottom = 5.dp),
                                maxLines = if (smallDescription) 4 else Int.MAX_VALUE,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                if (smallDescription) {
                                    Text(text = "більше",
                                        lineHeight = 20.scaledSp(),
                                        fontSize = 20.scaledSp(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.clickable {
                                            smallDescription = !smallDescription
                                        },
                                        color = Color.Black,
                                    )
                                } else {
                                    Text(text = "менше",
                                        lineHeight = 20.scaledSp(),
                                        fontSize = 20.scaledSp(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.clickable {
                                            smallDescription = !smallDescription
                                        },
                                        color = Color.Black,
                                    )
                                }
                                Text(text = "до рецептів",
                                    lineHeight = 20.scaledSp(),
                                    fontSize = 20.scaledSp(),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable {
//                                    selectedCategory.value = item
                                        viewModel.selectCategoty(index)
                                        navController.navigate(Screens.MainScreen.rout)
                                        viewModel.topBar = element
                                    },
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}