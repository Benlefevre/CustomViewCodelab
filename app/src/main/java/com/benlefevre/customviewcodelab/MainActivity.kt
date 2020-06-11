package com.benlefevre.customviewcodelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.benlefevre.customviewcodelab.clip.ClippingActivity
import com.benlefevre.customviewcodelab.fan.FanActivity
import com.benlefevre.customviewcodelab.findme.FindMeActivity
import com.benlefevre.customviewcodelab.minipaint.MiniPaintActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fan_btn.setOnClickListener {
            startActivity(Intent(this,
                FanActivity::class.java))
        }
        mini_paint_btn.setOnClickListener {
            startActivity(Intent(this, MiniPaintActivity::class.java))
        }

        clipping_btn.setOnClickListener {
            startActivity(Intent(this,
                ClippingActivity::class.java))
        }

        find_me_btn.setOnClickListener {
            startActivity(Intent(this, FindMeActivity::class.java))
        }
    }
}