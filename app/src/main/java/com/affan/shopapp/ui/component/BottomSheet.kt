package com.affan.shopapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.affan.shopapp.R
import com.affan.shopapp.ui.screen.detail.ProductCounter
import com.affan.shopapp.ui.theme.ShopAppTheme
import com.affan.shopapp.utils.convertCurency

@Composable
fun BottomSheet(price: Int, qty: Int, image: String, onAddToCart: (count: Int) -> Unit) {
    var totalPrice by rememberSaveable { mutableStateOf(price) }
    var countOrder by rememberSaveable { mutableStateOf(qty) }
    Column(
        modifier = Modifier
            .heightIn(max = 300.dp)
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(3.dp)
                .width(70.dp)
                .background(Color.LightGray)
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = stringResource(R.string.product_preview),
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = convertCurency(totalPrice), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Divider(
                    modifier = Modifier
                        .height(1.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(text = stringResource(R.string.total))

                    ProductCounter(
                        orderId = 1,
                        orderCount = countOrder,
                        onProductIncreased = { countOrder++ },
                        onProductDecreased = { if (countOrder > 0) countOrder-- })
                    totalPrice = price * countOrder
                }
            }

            OrderButton(onClick = { onAddToCart(countOrder) }, modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.beli), enabled = countOrder >= 1)
        }

    }
}
@Composable
fun OrderButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
   Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)

    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
@Preview
@Composable
fun DetailPrev() {
    ShopAppTheme() {
        BottomSheet(100, 2, "< >", onAddToCart = {})
    }
}