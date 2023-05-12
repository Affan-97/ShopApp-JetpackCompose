package com.affan.shopapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.affan.shopapp.ui.theme.ShopAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemList(
    title: String,
    price: String,
    imageUrl: String,
    id: Int,
    navigateDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {


    Card(
        onClick = { navigateDetail(id) } ,
        colors = CardDefaults.cardColors(containerColor = Color.Cyan),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(40.dp))
            Column() {
                Text(text = title)
                Text(text = price)
            }
        }
    }
}
/*

@Preview(showBackground = true)
@Composable
fun ItemPrev() {
    ShopAppTheme() {
        ItemList(
            "Buah",
            "5000",
            "https://www.applesfromny.com/wp-content/uploads/2020/05/20Ounce_NYAS-Apples2.png"
       , navigateDetail = {}
        )
    }
}*/
