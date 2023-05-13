package com.affan.shopapp.ui.screen.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.affan.shopapp.R
import com.affan.shopapp.common.UiState
import com.affan.shopapp.di.Injection
import com.affan.shopapp.model.CartState
import com.affan.shopapp.ui.ViewModelFactory
import com.affan.shopapp.ui.component.CartList
import com.affan.shopapp.ui.component.OrderButton
import com.affan.shopapp.utils.convertCurency

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedCart()
            }

            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    navigateDetail = {},
                    navigateBack = navigateBack,
                    deleteItem = { id -> viewModel.deleteProduct(id) },
                    onProductCountChanged = { id, qty ->
                        viewModel.updateOrderReward(id, qty)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartContent(
    state: CartState,
    modifier: Modifier = Modifier,
    navigateDetail: (Int) -> Unit,
    deleteItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
) {

    Scaffold(topBar = {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null
                    )
                }
                Text(
                    text = stringResource(id = R.string.detail_title),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset((-24).dp)
                )
            }
            Divider(modifier = Modifier.shadow(1.dp))
        }
    }) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxHeight()

        ) {
            LazyColumn(

                contentPadding = it,

                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                itemsIndexed(state.listCartItem) { index, data ->
                    CartList(
                        id = data.item.id,
                        title = data.item.name,
                        image = data.item.image,
                        price = convertCurency(data.item.price),
                        qty = data.qty,
                        onItemDelete = { id -> deleteItem(id) },
                        onProductCountChanged = { id, qty -> onProductCountChanged(id, qty) },
                        context = LocalContext.current
                    )
                }

            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier.shadow(1.dp).padding( 16.dp) ) {
                Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = stringResource(R.string.total_price))
                    Text(text = convertCurency(state.totalPrice), fontWeight = FontWeight.Bold)
                }
            OrderButton(text =" Beli Sekarang") {
            }

            }
        }
    }

}