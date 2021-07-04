package com.arturlasok.composeArturApp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ListaWiadomosciLazyCol(
    wiadomosci: List<Wiadomosc>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage:() -> Unit,
    onnavController: NavController,
    viewModeltoSwipe: ListaWiadomosciViewModel
) {
    val isRefreshing by viewModeltoSwipe.isRefreshing.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ){

        if (wiadomosci.isNotEmpty()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModeltoSwipe.onTriggerEvent(ListaWiadomosciEvent.NewSearchEvent) },
            ) {

                LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {

                    itemsIndexed(
                        items = wiadomosci
                    ) { index, item ->

                        //pagination
                        onChangeScrollPosition(index)
                        // && !loading
                        if ((index + 1) >= (page * PAGE_SIZE)) {
                            onTriggerNextPage()
                        }


                        ListaWiadomosciCard(wiadomosc_vm = item, onClick = {
                            if (item.domid != null) {
                                val route = Screen.SzczegolyWiadomosci.route + "/${item.domid}"
                                onnavController.navigate(route)
                            }

                        })

                    }
                }

            }
        }
    }


}
