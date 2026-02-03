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
data class OverviewItem(val title: String, val onClick: () -> Unit)


@Composable
fun StoreOverviewScreen(
    onOrdersClicked: () -> Unit,
    onProductsClicked: () -> Unit,
    onCustomersClicked: () -> Unit,
    onInventoryClicked: () -> Unit,
) {

    StoreOverviewContent(
        OverviewState(
            listOf(
                OverviewItem("Orders", onOrdersClicked),
                OverviewItem("Products", onProductsClicked),
                OverviewItem("Customers", onCustomersClicked),
                OverviewItem("Inventory", onInventoryClicked)
            )
        )
    )
}

@Composable
fun StoreOverviewContent(
    state: OverviewState,
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        items(state.items) { item ->
            OverviewListItem(item.title, item.onClick)
        }
    }
}

@Composable
fun OverviewListItem(title: String, onItemClicked: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onItemClicked),
        headlineContent = { Text(title) }
    )
}

@Preview(name = "Store Overview", showSystemUi = true)
@Composable
fun PreviewStoreOverviewScreen() {
    ASWCMSTheme {
        StoreOverviewContent(
            OverviewState(
                listOf(
                    OverviewItem("Orders", {}),
                    OverviewItem("Products", {}),
                    OverviewItem("Customers", {}),
                    OverviewItem("Inventory", {})
                )
            )
        )
    }
}