package com.example.aswcms.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aswcms.ui.theme.ASWCMSTheme

@Composable
fun StoreOverviewScreen() {

    StoreOverviewContent(
        OverviewState(
            listOf(
                OverViewItem(OverviewItemId.ORDERS, "Orders"),
                OverViewItem(OverviewItemId.PRODUCTS, "Products"),
                OverViewItem(OverviewItemId.CUSTOMERS, "Customers"),
                OverViewItem(OverviewItemId.INVENTORY, "Inventory")
            )
        )
    )
}

data class OverviewState(val items: List<OverViewItem>)
data class OverViewItem(val id: OverviewItemId, val title: String)

enum class OverviewItemId {
    ORDERS,
    PRODUCTS,
    CUSTOMERS,
    INVENTORY
}

@Composable
fun StoreOverviewContent(state: OverviewState) {
    val onItemClicked: (OverviewItemId) -> Unit = {

    }
    LazyColumn(Modifier.fillMaxSize()) {
        items(state.items) { item ->
            OverviewListItem(item, onItemClicked)
        }
    }
}

@Composable
fun OverviewListItem(item: OverViewItem, onItemClicked: (OverviewItemId) -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = { onItemClicked(item.id) }),
        headlineContent = { Text(item.title) }
    )
}

@Preview(name = "Store Overview", showSystemUi = true)
@Composable
fun PreviewStoreOverviewScreen() {
    ASWCMSTheme {
        StoreOverviewScreen()
    }
}