package com.affan.shopapp.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.affan.shopapp.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://firebasestorage.googleapis.com/v0/b/bangkit-dashboard/o/production%2F2023-B1%2Fprofiles%2F79a87ee6-c23d-425b-919d-a0d6a7365bb4.jpeg?alt=media&token=9e26dbb1-5d55-4461-be0f-7a8ae44d80b2",
            contentDescription = "User Image",
            modifier = Modifier
                .size(300.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.Name), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.email), style = TextStyle(fontSize = 16.sp))
    }
}