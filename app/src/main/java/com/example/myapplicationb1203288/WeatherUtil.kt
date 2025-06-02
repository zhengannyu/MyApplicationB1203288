package com.example.myapplicationb1203288

import android.content.Context
import com.example.myapplicationb1203288.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun getRetrofitWithGRCA(context: Context): Retrofit {
    try {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val caInput: InputStream = context.resources.openRawResource(R.raw.grca_root)
        val cert = certificateFactory.generateCertificate(caInput)
        val ca = cert as? X509Certificate
            ?: throw IllegalArgumentException("不是 X509 憑證")

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("grca", ca)
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        ).apply { init(keyStore) }

        val trustManager = trustManagerFactory.trustManagers
            .firstOrNull { it is X509TrustManager } as? X509TrustManager
            ?: throw IllegalStateException("沒有 X509TrustManager")

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustManager), SecureRandom())
        }

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://opendata.cwa.gov.tw/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    } catch (e: Exception) {
        throw RuntimeException("SSL 初始化失敗：${e.message}", e)
    }
}
