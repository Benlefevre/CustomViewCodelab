package com.benlefevre.customviewcodelab.clip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ClippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ClippingView(
                this
            )
        )
    }
}