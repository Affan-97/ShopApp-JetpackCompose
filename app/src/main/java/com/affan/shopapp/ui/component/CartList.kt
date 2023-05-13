package com.affan.shopapp.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun CartList(
    id: Int,
    title: String,
    image: String,
    price: String,
    qty: Int,
    onItemDelete: (Id: Int) -> Unit,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
    modifier: Modifier = Modifier,
    context:Context
) {


    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = modifier.size(100.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = price, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }
            Divider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                IconButton(onClick = {
                    onItemDelete(id)

                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Item")
                }
                ProductCounter(
                    orderId = id,
                    orderCount = qty,
                    onProductIncreased = { onProductCountChanged(id, qty + 1) },
                    onProductDecreased = { if (qty >= 1) onProductCountChanged(id, qty - 1) }
                )
            }
        }
    }
}

