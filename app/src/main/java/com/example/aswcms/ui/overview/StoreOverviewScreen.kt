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
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aswcms.ui.theme.ASWCMSTheme
import kotlinx.serialization.Serializable


data class OverviewState(val items: List<OverviewItem>)
data class OverviewItem(val id: OverviewItemId, val title: String)

enum class OverviewItemId {
    ORDERS,
    PRODUCTS,
    CUSTOMERS,
    INVENTORY,
}


@Composable
fun StoreOverviewScreen() {

    val onItemClicked: (OverviewItemId) -> Unit = { id ->

    }
    StoreOverviewContent(
        OverviewState(
            listOf(
                OverviewItem(OverviewItemId.ORDERS, "Orders"),
                OverviewItem(OverviewItemId.PRODUCTS, "Products"),
                OverviewItem(OverviewItemId.CUSTOMERS, "Customers"),
                OverviewItem(OverviewItemId.INVENTORY, "Inventory")
            )
        ),
        onItemClicked
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
                    OverviewItem(OverviewItemId.ORDERS, "Orders"),
                    OverviewItem(OverviewItemId.PRODUCTS, "Products"),
                    OverviewItem(OverviewItemId.CUSTOMERS, "Customers"),
                    OverviewItem(OverviewItemId.INVENTORY, "Inventory")
                )
            ),
            { }
        )
    }
}