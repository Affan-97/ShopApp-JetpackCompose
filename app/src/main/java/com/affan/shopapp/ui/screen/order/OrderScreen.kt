package com.affan.shopapp.ui.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.affan.shopapp.R
import com.affan.shopapp.common.UiState
import com.affan.shopapp.di.Injection
import com.affan.shopapp.model.OrderItem
import com.affan.shopapp.ui.ViewModelFactory
import com.affan.shopapp.ui.component.OrderList


@Composable
fun OrderScreen(
    viewModel: OrderViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ), modifier: Modifier = Modifier
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                viewModel.getOrders()
            }

            is UiState.Success -> {
                OrderContent(list = uiState.data, modifier = modifier)
            }

            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderContent(
    list: List<OrderItem>,

    modifier: Modifier
) {
    Scaffold(topBar = {
        Row(
            modifier = Modifier.shadow(1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.transaction),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)

            )

            Divider(modifier = Modifier.shadow(1.dp))
        }
    }) { values ->
        if (list.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .padding(values)
                    .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(list) {
                    OrderList(
                        index = (list.indexOf(it) + 1),
                        item = it.listOrderItem.size.toString(),
                        total = it.totalPrice
                    )
                }


            }
        } else {
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    Icon(imageVector = Icons.Default.Warning , contentDescription = stringResource(R.string.warning_icon))
                Text(text = stringResource(R.string.empty_transaction))
            }
        }
    }
}