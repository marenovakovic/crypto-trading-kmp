package tech.mapps.cryptotradingkmp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

fun intervalFlow(
    interval: Duration,
    initialDelay: Duration = Duration.ZERO,
) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(interval)
    }
}