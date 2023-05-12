package com.affan.shopapp.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.affan.shopapp.common.UiState
import com.affan.shopapp.di.Injection
import com.affan.shopapp.model.Product
import com.affan.shopapp.ui.ViewModelFactory
import com.affan.shopapp.ui.component.ItemList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllData()
            }

            is UiState.Success -> {
                HomeContent(
                    order = uiState.data,
                    modifier = modifier,
                    navigateDetail = navigateDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    order: List<Product>,
    modifier: Modifier = Modifier,
    navigateDetail: (Int) -> Unit
) {
    LazyColumn(

        contentPadding = PaddingValues(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(order) { data ->
            ItemList(
                imageUrl = data.image,
                title = data.name,
                price = data.price.toString(),
                id = data.id,
                navigateDetail = { navigateDetail(data.id) },
                modifier = modifier
            )
        }
    }
}