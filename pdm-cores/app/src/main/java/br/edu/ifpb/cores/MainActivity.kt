package br.edu.ifpb.cores

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import java.lang.String

class MainActivity : AppCompatActivity() {
    private lateinit var sbRed: SeekBar
    private lateinit var sbGreen: SeekBar
    private lateinit var sbBlue: SeekBar
    private lateinit var tvResult: TextView
    private lateinit var llResult: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.llResult = findViewById(R.id.llResult)

        this.tvResult = findViewById(R.id.tvResult)

        this.sbRed = findViewById(R.id.sbRed)
        this.sbRed.progressTintList = ColorStateList.valueOf(Color.RED)
        this.sbRed.thumbTintList = ColorStateList.valueOf(Color.RED)

        this.sbGreen = findViewById(R.id.sbGreen)
        this.sbGreen.progressTintList = ColorStateList.valueOf(Color.GREEN)
        this.sbGreen.thumbTintList = ColorStateList.valueOf(Color.GREEN)

        this.sbBlue = findViewById(R.id.sbBlue)
        this.sbBlue.progressTintList = ColorStateList.valueOf(Color.BLUE)
        this.sbBlue.thumbTintList = ColorStateList.valueOf(Color.BLUE)

        this.changeResultColor()

        this.sbRed.setOnSeekBarChangeListener(OnChange())
        this.sbGreen.setOnSeekBarChangeListener(OnChange())
        this.sbBlue.setOnSeekBarChangeListener(OnChange())
    }

    private fun changeResultColor() {
        val red = this.sbRed.progress
        val green = this.sbGreen.progress
        val blue = this.sbBlue.progress
        val color = Color.rgb(red, green, blue)

        if (
            255 - red < red - 0 &&
            255 - green < green - 0 &&
            255 - blue < blue - 0
        ) {
            this.tvResult.setBackgroundColor(Color.BLACK)
        } else {
            this.tvResult.setBackgroundColor(Color.WHITE)
        }

        this.llResult.setBackgroundColor(color)
        this.tvResult.setTextColor(color)
        this.tvResult.text = String.format("#%02x%02x%02x", red, green, blue)
    }

    inner class OnChange : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            this@MainActivity.changeResultColor()
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}

        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }
}
