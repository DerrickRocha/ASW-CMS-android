package com.example.aswcms.ui.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aswcms.R
import com.example.aswcms.ui.theme.ASWCMSTheme
import com.example.aswcms.ui.viewmodels.OptionState
import com.example.aswcms.ui.viewmodels.ProductDetailsState
import com.example.aswcms.ui.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(viewModel: ProductDetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    ProductDetailsScreenContent(
        state,
        viewModel::onNameChange,
        viewModel::onDescriptionChange,
        viewModel::onPriceChange,
        viewModel::onActiveChange,
        viewModel::onSalePriceChange,
        viewModel::onTrackInventoryChange,
        viewModel::onInventoryQuantityChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    state: ProductDetailsState,
    onNameChange: (name: String) -> Unit,
    onDescriptionChange: (description: String) -> Unit,
    onPriceChange: (price: String) -> Unit,
    onActiveChange: (isActive: Boolean) -> Unit,
    onSalePriceChange: (salePrice: String) -> Unit,
    onTrackInventoryChange: (track: Boolean) -> Unit,
    onInventoryQuantityChange: (inventoryQuantity: String) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Adds 8.dp vertical space between all items
        ) {
            ImageSection()
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("product_name_text_field"),
                onValueChange = onNameChange,
                value = state.productName,
                label = { Text(stringResource(R.string.product_name)) },
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .testTag("product_description_text_field"),
                onValueChange = onDescriptionChange,
                value = state.description,
                label = { Text(stringResource(R.string.product_description)) })
            PriceSection(
                priceString = state.priceString,
                isPriceValid = state.isPriceValid,
                salePriceString = state.salePriceString,
                isSalePriceValid = state.isSalePriceValid,
                onPriceChange,
                onSalePriceChange
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.isActive,
                    onCheckedChange = onActiveChange
                )
                Text("Active")
            }
            InventorySection(state.trackInventory, onTrackInventoryChange, state.inventoryQuantityText, state.isQuantityValid, onInventoryQuantityChange)
            ProductOptionsSection(state.options)
        }
    }
}

@Composable
fun InventorySection(
    track: Boolean,
    onTrackInventoryChange: (track: Boolean) -> Unit,
    quantityText: String,
    isQuantityValid: Boolean,
    onQuantityChange: (quantity: String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TrackInventorySwitch(Modifier.weight(1f), track, onTrackInventoryChange)
        InventoryQuantityField(quantityText, isQuantityValid, onQuantityChange)
    }
}

@Composable
fun InventoryQuantityField(
    quantityString: String,
    isQuantityValid: Boolean,
    onQuantityChange: (quantity: String) -> Unit
) {
    TextField(
        modifier = Modifier.width(120.dp),
        onValueChange = onQuantityChange,
        value = quantityString,
        isError = !isQuantityValid,
        label = { Text(stringResource(R.string.quantity)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun TrackInventorySwitch(
    modifier: Modifier,
    track: Boolean,
    onTrackInventoryChange: (track: Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Switch(
            checked = track,
            onCheckedChange = onTrackInventoryChange,
        )
        Text("Track Inventory")
    }

}

@Composable
fun ProductOptionsSection(options: List<OptionState>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Options", fontWeight = FontWeight.Bold)
        Text("Add different options for your product, like variety of sizes and colors.")
        // add labels for name and choice column
        options.forEach { option ->
            OptionListItem(option)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {}) {
            Text("Add option")
        }
    }
}

@Composable
fun OptionListItem(option: OptionState) {
    ListItem(
        modifier = Modifier,
        headlineContent = { Text(option.optionsString) },
        leadingContent = { Text(option.name) },
        trailingContent = { OptionActions() }
    )
}


@Composable
fun OptionActions() {
    Row {
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = ""
            )
        }
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = ""
            )
        }
    }

}

@Composable
fun PriceSection(
    priceString: String,
    isPriceValid: Boolean,
    salePriceString: String,
    isSalePriceValid: Boolean,
    onPriceChange: (price: String) -> Unit,
    onSalePriceChange: (salePrice: String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        TextField(
            modifier = Modifier.weight(1f),
            value = priceString,
            label = { Text(stringResource(R.string.price)) },
            onValueChange = onPriceChange,
            placeholder = { Text(stringResource(R.string.price)) },
            isError = !isPriceValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        TextField(
            modifier = Modifier.weight(1f),
            value = salePriceString,
            label = { Text(stringResource(R.string.sale_price)) },
            onValueChange = onSalePriceChange,
            isError = !isSalePriceValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Composable
fun ImageSection() {

}

@Composable
@Preview(showSystemUi = true)
fun ProductDetailsContentPreview() {
    ASWCMSTheme {
        Box(Modifier.safeDrawingPadding()) {
            ProductDetailsScreenContent(
                ProductDetailsState(),
                {},
                { },
                {},
                {},
                {},
                {},
                {}
            )
        }
    }
}