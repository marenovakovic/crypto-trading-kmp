package tech.mapps.cryptotradingkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import crypto_trading_kmp.composeapp.generated.resources.Res
import crypto_trading_kmp.composeapp.generated.resources.ticker
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tech.mapps.cryptotradingkmp.theme.AppTheme

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(text = stringResource(Res.string.ticker)) },
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(color = MaterialTheme.colorScheme.background),
    )
}

@Preview
@Composable
private fun SearchFieldEmptyPreview() {
    AppTheme {
        SearchField(
            query = "",
            onQueryChange = {},
        )
    }
}

@Preview
@Composable
private fun SearchFieldFilledInPreview() {
    AppTheme {
        SearchField(
            query = "BTC",
            onQueryChange = {},
        )
    }
}