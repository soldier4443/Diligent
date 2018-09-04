package com.turastory.diligenttest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val redirectUrl = "diligenttest://authorize"
    private var code: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authButton.setOnClickListener {
            code ?: run {
                val url = Uri.parse("https://github.com/login/oauth/authorize?" +
                    "client_id=${BuildConfig.GITHUB_CLIENT_ID}&" +
                    "redirect_url=$redirectUrl")
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data

        if (uri?.toString()?.startsWith(redirectUrl) == true) {
            uri.getQueryParameter("code")?.let {
                this.code = it
                showToast("Authenticate success!")
            } ?: uri.getQueryParameter("error")?.let {
                showError(it)
            }
        }
    }

    private fun showError(string: String) {
        showToast("Error: $string")
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}
