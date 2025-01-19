package com.example.danmuse.ui.auth.vkHost

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.danmuse.components.auth.vkHost.VkHostComponent
import com.example.mvi.auth.vkHost.VkHostIntent

const val AUTH_URL = "https://oauth.vk.com/authorize?client_id=6463690&scope=1073737727&redirect_uri=https://oauth.vk.com/blank.html&display=page&response_type=token&revoke=1"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VkHost(
    component: VkHostComponent
) {
    Column {
        AndroidView(factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString()
                        url?.let { url ->
                            if (url.contains("access_token="))
                                component.processIntent(VkHostIntent.GetToken(url))
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                loadUrl(AUTH_URL)
            }
        })
    }
}