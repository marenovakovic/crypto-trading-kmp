package tech.mapps.cryptotradingkmp

import kotlinx.coroutines.flow.flowOf

internal class IosConnectivityStatusTrackerFactory : ConnectivityStatusTrackerFactory {
    override fun create() = IosConnectivityStatusTracker()
}

internal class IosConnectivityStatusTracker : ConnectivityStatusTracker {
    override fun observeConnectivity() = flowOf(ConnectivityStatus.Available)
}