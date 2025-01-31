package com.example.danmuse.ui.auth.vkHost

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.danmuse.components.auth.vkHost.VkHostComponent
import com.example.mvi.auth.vkHost.VkHostIntent

const val AUTH_URL = "https://oauth.vk.com/authorize?client_id=6463690&scope=1073737727&redirect_uri=https://oauth.vk.com/blank.html&display=page&response_type=token&revoke=1"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VkHost(
    component: VkHostComponent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp)
    ) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString()
                        url?.let {
                            if (it.contains("access_token="))
                                component.processIntent(VkHostIntent.GetToken(it))
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                loadUrl(AUTH_URL)
            }
        })
    }
}