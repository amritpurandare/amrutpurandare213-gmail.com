package com.amrit.circulargraphview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val innerCircle = findViewById<CircularGraph>(R.id.inner_circle)
        innerCircle.setUsedLimit(40)
        innerCircle.setMaxLimit(50)
        innerCircle.setFilledColor(Color.parseColor("#FF0000"))
        innerCircle.setDefaultColor(Color.parseColor("#E3E9ED"))
        innerCircle.setDefaultWidth(resources.getDimension(R.dimen.default_circumference_width))
        innerCircle.setFilledWidth(resources.getDimension(R.dimen.default_circumference_width))

        val outerCircle = findViewById<CircularGraph>(R.id.outer_circle)
        outerCircle.setUsedLimit(40)
        outerCircle.setMaxLimit(100)
        outerCircle.setFilledColor(Color.parseColor("#0000FF"))
        outerCircle.setDefaultColor(Color.parseColor("#E3E9ED"))
        outerCircle.setDefaultWidth(resources.getDimension(R.dimen.default_circumference_width))
        outerCircle.setFilledWidth(resources.getDimension(R.dimen.default_circumference_width))

    }
}
