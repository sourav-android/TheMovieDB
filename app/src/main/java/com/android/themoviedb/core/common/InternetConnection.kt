package com.android.themoviedb.core.common




import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

sealed class NetworkStatus {
    data object Unknown: NetworkStatus()
    data object Connected: NetworkStatus()
    data object Disconnected: NetworkStatus()
}

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}

class NetworkConnectivityServiceImpl (
    context: Context
): NetworkConnectivityService  {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val networkStatus: Flow<NetworkStatus> = callbackFlow {
        val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.Connected)
            }

            override fun onUnavailable() {
                trySend(NetworkStatus.Disconnected)
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.Disconnected)
            }

        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, connectivityCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)

}