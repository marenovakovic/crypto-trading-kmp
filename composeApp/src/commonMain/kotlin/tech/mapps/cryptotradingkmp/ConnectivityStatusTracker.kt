package tech.mapps.cryptotradingkmp

import kotlinx.coroutines.flow.Flow

internal sealed interface ConnectivityStatus {
    data object Available : ConnectivityStatus
    data object Unavailable : ConnectivityStatus
}

internal interface ConnectivityStatusTrackerFactory {
    fun create(): ConnectivityStatusTracker
}

internal fun interface ConnectivityStatusTracker {
    fun observeConnectivity(): Flow<ConnectivityStatus>
}