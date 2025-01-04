package com.project.shopz.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonColors
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.shimmer
import com.project.shopz.R
import com.project.shopz.model.Category
import com.project.shopz.model.ProductItem
import com.project.shopz.ui.navigation.Screen
import com.project.shopz.ui.screens.CartManager.CartManager
import com.project.shopz.viewmodel.ShopzViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ShopzViewModel,
    onProductClick: (Int) -> Unit,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    // Remember scroll states
    val categoryScrollState = rememberLazyListState()
    val productScrollState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(R.drawable.shop),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Shopz",
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(R.drawable.cart),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(47.dp)
                                .clickable { navController.navigate("cart") }

                        )

                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
            )
        },
        modifier = Modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(top = 112.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Shop By Category",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (categories.isEmpty()) {
                LazyRow {
                    items(5) { ShimmerCategoryItem() }
                }
            } else {
                LazyRow(state = categoryScrollState) {
                    items(categories) { category ->
                        categoryItem(category)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Curated for You",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                when {
                    uiState.isLoading -> {
                        Log.d("HomeScreen", "Loading state is true, showing progress indicator")
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = productScrollState,
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(6) { ShimmerProductItem() }
                        }
                    }

                    uiState.error.isNotBlank() -> {
                        Text(text = uiState.error)
                    }

                    uiState.data != null -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = productScrollState,
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.data!!.toList()) { item: ProductItem ->
                                productItem(productItem = item,
                                    onItemClick = { onProductClick(item.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun categoryItem(
    category: Category
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(34.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = category.image,
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun productItem(
    productItem: ProductItem,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        var isiconselected by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(productItem.images) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Product image",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            IconButton(
                onClick = { CartManager.addItem(productItem)
                          isiconselected = false },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(imageVector = if (isiconselected) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = "Add to Favourites",
                    tint = Color.White)
            }
        }
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(
                text = productItem.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Price: \$${productItem.price}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

        }
    }
}


@Composable
fun ShimmerCategoryItem() {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .padding(4.dp)
            .placeholder(
                visible = true,
                color = Color.LightGray.copy(alpha = 0.3f), // Base color
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White, // Highlight color
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 1000, // Duration of the shimmer effect
                            easing = LinearEasing
                        )
                    )
                )
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {}
}

@Composable
fun ShimmerProductItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
            .placeholder(
                visible = true,
                color = Color.LightGray.copy(alpha = 0.3f),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                )
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {}
}
