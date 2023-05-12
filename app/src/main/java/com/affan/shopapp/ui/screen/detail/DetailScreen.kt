package com.affan.shopapp.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.affan.shopapp.R
import com.affan.shopapp.common.UiState
import com.affan.shopapp.di.Injection
import com.affan.shopapp.ui.ViewModelFactory
import com.affan.shopapp.ui.theme.ShopAppTheme
import com.affan.shopapp.utils.convertCurency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDetail(id)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.image,
                    name = data.name,
                    desc = data.desc,
                    price = data.price,
                    stock = data.stock,
                    onBackClick = navigateBack,
                    onAddToCart = { }
                )
            }

            is UiState.Error -> {}
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    image: String,
    name: String,
    desc: String,
    stock: Int,
    price: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(topBar = {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
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

        Box(modifier = Modifier.padding(it)) {
           Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 24.dp)) {
               Column(
                   verticalArrangement = Arrangement.spacedBy(24.dp),
                   modifier = Modifier

                       .fillMaxWidth()
               ) {
                   Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                   Box(
                       modifier = modifier
                           .fillMaxWidth(),
                       contentAlignment = Alignment.Center
                   ) {

                       AsyncImage(
                           model = image,
                           contentDescription = null,
                           alignment = Alignment.Center,
                           modifier = Modifier.size(160.dp)
                       )


                   }
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       Text(text = convertCurency(price), color = MaterialTheme.colorScheme.primary)
                       Row(
                           verticalAlignment = Alignment.Bottom,
                           horizontalArrangement = Arrangement.spacedBy(4.dp)
                       ) {
                           Text(text = stock.toString(), fontWeight = FontWeight.Bold)
                           Text(text = stringResource(id = R.string.stock), fontSize = 12.sp)
                       }
                   }

                   Column(modifier = Modifier.fillMaxWidth()) {
                       Text(text = "Description", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                       Text(text = desc, textAlign = TextAlign.Justify)
                   }
               }
               Button(onClick = {  }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
                   Text(text = "Pesan")
               }
           }
        }
    }
}

@Preview
@Composable
fun DetailPrev() {
    ShopAppTheme() {
        DetailScreen(id = 1, navigateBack = { }) {

        }
    }
}

