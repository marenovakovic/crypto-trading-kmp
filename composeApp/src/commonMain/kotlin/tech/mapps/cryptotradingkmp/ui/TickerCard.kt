package tech.mapps.cryptotradingkmp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import tech.mapps.cryptotradingkmp.Ticker
import tech.mapps.cryptotradingkmp.theme.AppTheme

@Composable
fun TickerCard(
    ticker: Ticker,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://res.cloudinary.com/dxi90ksom/image/upload/${ticker.ticker}",
                    error = rememberVectorPainter(Icons.Default.ErrorOutline),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = ticker.ticker,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color =
                        if (ticker.change24hPercentage > 0) Color(0XFF12B32D)
                        else Color(0xFFCC3F29),
                    ),
                    text = "${ticker.change24hPercentage.sign()}${ticker.change24hPercentage}%",
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = ticker.price,
                )
            }
        }
    }
}

private fun Float.sign() = if (this > 0) "+" else ""

@Preview
@Composable
private fun TickerCardPositiveChangePreview() {
    val ticker = Ticker(
        ticker = "BTC",
        price = "69863 USD",
        change24hPercentage = 2.10f,
    )
    AppTheme {
        TickerCard(ticker = ticker)
    }
}

@Preview
@Composable
private fun TickerCardNegativeChangePreview() {
    val ticker = Ticker(
        ticker = "ETH",
        price = "3981 USD",
        change24hPercentage = -1.80f,
    )
    AppTheme {
        TickerCard(ticker = ticker)
    }
}