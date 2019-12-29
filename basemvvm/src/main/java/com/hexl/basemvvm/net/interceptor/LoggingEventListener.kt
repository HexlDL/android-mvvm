package com.hexl.basemvvm.net.interceptor

import com.hexl.basemvvm.utils.LogUtil
import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * @desc okhttp4后提供的日志事件拦截器,该拦截器不应该认为是稳定的
 * @author Hexl
 * @date 2019/11/13
 */
class LoggingEventListener : EventListener() {
    override fun callEnd(call: Call) {
        super.callEnd(call)
        LogUtil.e("callEnd")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        LogUtil.e("callFailed")
    }

    override fun callStart(call: Call) {
        super.callStart(call)
        LogUtil.e("callStart")
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        LogUtil.e("connectEnd")
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        LogUtil.e("connectFailed")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        LogUtil.e("connectionAcquired")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        LogUtil.e("connectionReleased")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        LogUtil.e("dnsEnd")
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        LogUtil.e("dnsStart")
    }

    override fun proxySelectEnd(call: Call, url: HttpUrl, proxies: List<Proxy>) {
        super.proxySelectEnd(call, url, proxies)
        LogUtil.e("proxySelectEnd")
    }

    override fun proxySelectStart(call: Call, url: HttpUrl) {
        super.proxySelectStart(call, url)
        LogUtil.e("proxySelectStart")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        LogUtil.e("requestBodyEnd")
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        LogUtil.e("requestBodyStart")
    }

    override fun requestFailed(call: Call, ioe: IOException) {
        super.requestFailed(call, ioe)
        LogUtil.e("requestFailed")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        LogUtil.e("requestHeadersEnd")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        LogUtil.e("requestHeadersStart")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        LogUtil.e("responseBodyEnd")
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        LogUtil.e("responseBodyStart")
    }

    override fun responseFailed(call: Call, ioe: IOException) {
        super.responseFailed(call, ioe)
        LogUtil.e("responseFailed")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        LogUtil.e("responseHeadersEnd")
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        LogUtil.e("responseHeadersStart")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        LogUtil.e("secureConnectEnd")
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
        LogUtil.e("secureConnectStart")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        LogUtil.e("connectStart")
    }

}