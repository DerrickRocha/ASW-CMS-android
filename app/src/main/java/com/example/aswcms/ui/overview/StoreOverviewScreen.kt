package com.example.aswcms.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aswcms.ui.theme.ASWCMSTheme


data class OverviewState(val items: List<OverviewItem>)
data class OverviewItem(val id: OverviewItemId, val title: String)

sealed interface OverviewItemId {
    data object Orders: OverviewItemId
    data class Products(val storeId: Int): OverviewItemId
    data object Customers: OverviewItemId
    data object Inventory: OverviewItemId
}


@Composable
fun StoreOverviewScreen(storeId: Int, onOverviewItemSelected: (OverviewItemId) -> Unit) {

    StoreOverviewContent(
        OverviewState(
            listOf(
                OverviewItem(OverviewItemId.Orders, "Orders"),
                OverviewItem(OverviewItemId.Products(storeId), "Products"),
                OverviewItem(OverviewItemId.Customers, "Customers"),
                OverviewItem(OverviewItemId.Inventory, "Inventory")
            )
        ),
        onOverviewItemSelected
    )
}

@Composable
fun StoreOverviewContent(state: OverviewState, onItemClicked: (OverviewItemId) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        items(state.items) { item ->
            OverviewListItem(item, onItemClicked)
        }
    }
}

@Composable
fun OverviewListItem(item: OverviewItem, onItemClicked: (OverviewItemId) -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = { onItemClicked(item.id) }),
        headlineContent = { Text(item.title) }
    )
}

@Preview(name = "Store Overview", showSystemUi = true)
@Composable
fun PreviewStoreOverviewScreen() {
    ASWCMSTheme {
        StoreOverviewContent(
            OverviewState(
                listOf(
                    OverviewItem(OverviewItemId.Orders, "Orders"),
                    OverviewItem(OverviewItemId.Products(1), "Products"),
                    OverviewItem(OverviewItemId.Customers, "Customers"),
                    OverviewItem(OverviewItemId.Inventory, "Inventory")
                )
            ),
            { }
        )
    }
}