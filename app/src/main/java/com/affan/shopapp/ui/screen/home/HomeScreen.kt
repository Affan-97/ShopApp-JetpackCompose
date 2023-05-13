package com.affan.shopapp.ui.screen.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.affan.shopapp.R
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
    navigateDetail: (Int) -> Unit,
    navigateToCart: () -> Unit
) {
    val query by viewModel.query
    var cartSize = 0
    viewModel.cartState.collectAsState(initial = UiState.Loading).value.let { cartState ->
        when (cartState) {
            is UiState.Loading -> {
                viewModel.getCart()
            }

            is UiState.Success -> {
                cartSize = cartState.data.size

            }

            is UiState.Error -> {}
        }
    }
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllData()
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    cartSize = cartSize,
                    viewModel = viewModel,
                    order = uiState.data,
                    modifier = modifier,
                    navigateDetail = navigateDetail,
                    navigateToCart = navigateToCart
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    query: String,
    cartSize: Int,
    viewModel: HomeViewModel,
    order: List<Product>,
    modifier: Modifier = Modifier,
    navigateDetail: (Int) -> Unit,
    navigateToCart: () -> Unit
) {
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
                modifier = Modifier
            )
            IconButton(onClick = { navigateToCart() }) {
                if (cartSize > 0) {
                    BadgedBox(badge = { Badge { Text(cartSize.toString()) } }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = stringResource(R.string.cart_hint),
                            modifier.size(28.dp)
                        )
                    }
                } else {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = stringResource(R.string.cart_hint),
                        modifier.size(28.dp)
                    )
                }
            }
        }
    }) {
        LazyColumn(

            contentPadding = PaddingValues(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(it)
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


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = TextStyle(fontSize = 16.sp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text("Cari")
        },
        modifier = modifier
            .padding(16.dp)

            .heightIn(min = 28.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )

    )
}