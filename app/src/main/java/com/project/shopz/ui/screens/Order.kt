package com.project.shopz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.shopz.model.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    cartItems: List<ProductItem>,
    onCheckoutClick: (Double) -> Unit
) {
    val totalPrice = cartItems.sumOf { it.price.toDouble() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Order Summary") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(cartItems) { item ->
                    OrderItem(item)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onCheckoutClick(totalPrice) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}

@Composable
fun OrderItem(productItem: ProductItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Use LazyRow to iterate, but display only first image
        LazyRow(
            modifier = Modifier.size(64.dp)
        ) {
            items(productItem.images) { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = productItem.title,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = productItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Price: \$${productItem.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}