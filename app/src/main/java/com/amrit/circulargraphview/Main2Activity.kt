package com.amrit.circulargraphview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val innerCircle = findViewById<CircleSeekBar>(R.id.inner_circle)
        innerCircle.curProcess = 30
        innerCircle.reachedColor = Color.parseColor("#00FF00")
        innerCircle.unreachedColor = Color.parseColor("#AAAAAA")

        val outerCircle = findViewById<CircleSeekBar>(R.id.outer_circle)
        outerCircle.curProcess = 60
        outerCircle.reachedColor = Color.parseColor("#0000FF")
        outerCircle.unreachedColor = Color.parseColor("#AAAAAA")

    }
}
