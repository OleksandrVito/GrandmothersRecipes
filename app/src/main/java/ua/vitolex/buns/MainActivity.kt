package ua.vitolex.buns

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.launch
import ua.vitolex.buns.presentation.components.AppBar
import ua.vitolex.buns.presentation.navigation.Screens
import ua.vitolex.buns.presentation.navigation.SetupNavHost
import ua.vitolex.buns.presentation.screen.MainViewModel
import ua.vitolex.buns.presentation.util.scaledSp
import ua.vitolex.buns.ui.theme.BunsTheme
import ua.vitolex.buns.ui.theme.OldGreen
import ua.vitolex.buns.ui.theme.OldGrey
import ua.vitolex.buns.ui.theme.OldOrange
import java.util.Timer
import kotlin.concurrent.schedule


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val vm: MainViewModel = hiltViewModel()
            val navController = rememberAnimatedNavController()

            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val title = currentBackStackEntry?.destination?.label

            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val categorys = stringArrayResource(id = R.array.drawer_list)

            val retrievedArrayList = getArrayListFromSharedPreferences("myArrayList")
            vm.addRetrievedArrayList(retrievedArrayList = retrievedArrayList)

            val selectedItemColor = Color.Gray.copy(0.4f)

            BunsTheme {

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = MaterialTheme.colorScheme.primary,
                            drawerShape = DrawerDefaults.shape,
                        ) {
                            LazyColumn(content = {
                                item { Spacer(Modifier.height(12.dp)) }
                                item {
                                    NavigationDrawerItem(
                                        label = {
                                            Text(
                                                text = "Головна",
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 2,
                                                fontSize = 22.scaledSp(),
                                                color = Color.Black,
                                            )
                                        },
                                        onClick = {
                                            scope.launch { drawerState.close() }
                                            vm.topBar = "Головна"
                                            navController.navigate(Screens.MScreen.rout)
                                        },
                                        selected = vm.selectedCategoty.value == -1 || vm.topBar == "Головна",
                                        modifier = Modifier.padding(
                                            top = 10.dp,
                                            start = 10.dp,
                                            end = 10.dp
                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = NavigationDrawerItemDefaults.colors(
                                            selectedTextColor = OldOrange,
                                            selectedContainerColor = selectedItemColor,
                                            unselectedContainerColor = OldGrey,
                                        )
                                    )
                                    categorys.forEach { item ->
                                        NavigationDrawerItem(
                                            label = {
                                                Text(
                                                    text = item,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 2,
                                                    fontSize = 22.scaledSp(),
                                                    color = Color.Black,
                                                )
                                            },
                                            selected = if (vm.selectedCategoty.value >= 0 && vm.topBar != "Головна") item == categorys[vm.selectedCategoty.value] else false,
                                            onClick = {
                                                scope.launch { drawerState.close() }
                                                vm.selectCategoty(categorys.indexOf(item))
                                                navController.navigate(Screens.MainScreen.rout)
                                                vm.topBar = item
                                            },
                                            modifier = Modifier.padding(
                                                top = 10.dp,
                                                start = 10.dp,
                                                end = 10.dp
                                            ),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = NavigationDrawerItemDefaults.colors(
                                                selectedTextColor = OldOrange,
                                                selectedContainerColor = selectedItemColor,
                                                unselectedContainerColor = OldGrey,
                                            )
                                        )
                                    }
                                }
                            })
                        }
                    },
                ) {
                    SetupNavHost(
                        navController = navController,
                        vm = vm,
                        selectedCategoryIndex = vm.selectedCategoty.value,
                        saveArrayListToSharedPreferences = ::saveArrayListToSharedPreferences,
                        retrievedArrayList = retrievedArrayList,
                        drawerState = drawerState
                    )
                }
            }
        }
        MobileAds.initialize(this)
    }

    private fun saveArrayListToSharedPreferences(key: String, arrayList: ArrayList<String>) {
        // Convert the ArrayList to a Set of strings
        val mySet = arrayList.toSet()
        // Get an instance of the SharedPreferences
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        // Get an instance of the SharedPreferences.Editor
        val editor = sharedPreferences.edit()

        // Put the Set of strings into the SharedPreferences.Editor
        editor.putStringSet(key, mySet)

        // Apply the changes to the SharedPreferences
        editor.apply()
        Log.d("MyLog", "Save")
    }

    private fun getArrayListFromSharedPreferences(key: String): ArrayList<String> {
        // Get an instance of the SharedPreferences
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        // Retrieve the Set of strings from SharedPreferences
        val mySet = sharedPreferences.getStringSet(key, emptySet())

        // Convert the Set of strings back to an ArrayList
        val myArrayList = arrayListOf<String>()
        myArrayList.addAll(mySet!!)

        return myArrayList
    }
}
