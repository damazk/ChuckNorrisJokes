package com.damask.chucknorrisjokes

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.damask.chucknorrisjokes.databinding.FragmentWebBinding
import kotlinx.android.synthetic.main.fragment_web.*


class WebFragment : Fragment(R.layout.fragment_web) {

    private val binding: FragmentWebBinding by viewBinding()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = binding.webView

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.loadUrl("http://www.icndb.com/api/")

        webView.webViewClient = object : WebViewClient() {
            val progressDialog = ProgressDialog(context)
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                progressBar.visibility = View.VISIBLE
                progressDialog.setMessage("Loading")
                progressDialog.show()
            }
            override fun onPageFinished(view: WebView?, url: String?) {
//                progressBar.visibility = View.GONE
                progressDialog.dismiss()
            }
        }

    }
}