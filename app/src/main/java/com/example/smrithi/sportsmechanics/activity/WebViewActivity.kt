package com.example.smrithi.sportsmechanics.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.smrithi.sportsmechanics.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

       val video_path = intent.getStringExtra("videoPath")
        webView.loadUrl(video_path)
    }
}
