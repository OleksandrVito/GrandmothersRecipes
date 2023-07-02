package ua.vitolex.buns.presentation.screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.vitolex.buns.R
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedItem = mutableStateOf(
        ListItem(
            "",
            "",
        )
    )
    val selectedItem: State<ListItem> = _selectedItem

    fun selectItem(item: ListItem) {
        viewModelScope.launch {
            _selectedItem.value = item
        }
    }

    private val _selectedCategoty = mutableStateOf(-1)
    val selectedCategoty: State<Int> = _selectedCategoty

    fun selectCategoty(index: Int) {
        viewModelScope.launch {
            _selectedCategoty.value = index
        }
    }

    data class ListItem(
        val title: String,
        val htmlName: String,
    )

    object IdArrayList {
        val listId = listOf(
//            R.array.pashtety,
            R.array.pyrizhky,
            R.array.hrinky,
            R.array.buterbrody,
            )
    }

    object DescryptionCategory {
        val listCategory = listOf(
//            "pashtety",
            "pyrizhky",
            "hrinky",
            "buterbrody",
        )
    }

    fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
        val list = ArrayList<ListItem>()
        val arrayList = context.resources.getStringArray(
            IdArrayList.listId[index]
        )
        arrayList.forEach { item ->

            val itemArray = item.split("|")
            list.add(
                ListItem(
                    itemArray[0],
                    itemArray[1]
                )
            )

        }
        return list
    }

    fun getDescriptionCategory(index: Int, context: Context): List<String> {
        val str =
            context.assets.open("txt/${DescryptionCategory.listCategory[index]}").bufferedReader()
                .use {
                    it.readText()
                }
        val arrayDescription = str.split("|")
        return arrayDescription
    }

    private var _favoriteRecipes = mutableStateOf(
       listOf(
            "Пиріжки з слойоного і напівслойоного тіста",
            "Пиріжки з сосисками. з дріжджового тіста смажені"
        )
    )

    val favoriteRecipes: State<List<String>> = _favoriteRecipes

    fun selectFavorite(title: String) {
        val list = _favoriteRecipes.value
        if (title in _favoriteRecipes.value) {
            viewModelScope.launch {
                _favoriteRecipes.value = list.minus(title)
            }
        } else {
            viewModelScope.launch {
                _favoriteRecipes.value = list.plus(title)
            }
        }
    }

    fun addRetrievedArrayList(retrievedArrayList:  ArrayList<String>){
        _favoriteRecipes.value = retrievedArrayList
    }

    var topBar = "Головна"
}