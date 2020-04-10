package com.amrit.circulargraphview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val innerCircle = findViewById<CircularGraph>(R.id.inner_circle)
//        innerCircle.curProcess = 50
//        innerCircle.reachedColor = Color.parseColor("#FF0000")
//        innerCircle.unreachedColor = Color.parseColor("#E3E9ED")


        val outerCircle = findViewById<CircularGraph>(R.id.outer_circle)
//        outerCircle.curProcess = 90
//        outerCircle.reachedColor = Color.parseColor("#00FF00")
//        outerCircle.unreachedColor = Color.parseColor("#E3E9ED")

    }
}
