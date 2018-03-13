package aspirity.afor.sapik.rssreader.common

import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

typealias Param = Pair<String, Any?>

enum class ConnectType { GET, POST }

fun String.getDataXml(): String? {
    val (_, _, result) = this.createRequest(ConnectType.GET).responseString()
    return handleResponse(result)
}

fun String.createRequest(type: ConnectType, vararg param: Param) = when (type) {
    ConnectType.GET -> this.httpGet(param.toList())
    ConnectType.POST -> this.httpPost(param.toList())
}

fun handleResponse(result: Result<String, FuelError>): String? = when (result) {
    is Result.Failure -> {
        Log.e("[Network]", "Error while download json data ${result.error}")
        Log.e("[Network]", result.error.response.url.toString())
        null
    }
    is Result.Success -> result.value
}
