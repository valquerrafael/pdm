package br.edu.ifpb.fofoca

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView

class PlayActivity : AppCompatActivity() {
    private lateinit var tvPlayFofoca: TextView
    private lateinit var rbPlayTrue: RadioButton
    private lateinit var rbPlayFalse: RadioButton
    private lateinit var btnPlayAnswer: Button
    private lateinit var pbPlayTimer: ProgressBar
    private lateinit var fofoca: Fofoca

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        this.fofoca = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("FOFOCA", Fofoca::class.java)
        } else {
            intent.getSerializableExtra("FOFOCA")
        } as Fofoca

        this.tvPlayFofoca = findViewById(R.id.tvPlayFofoca)
        this.rbPlayTrue = findViewById(R.id.rbPlayTrue)
        this.rbPlayFalse = findViewById(R.id.rbPlayFalse)
        this.btnPlayAnswer = findViewById(R.id.btnPlayAnswer)
        this.pbPlayTimer = findViewById(R.id.pbPlayTimer)

        this.btnPlayAnswer.setOnClickListener{ this.answer() }

        this.tvPlayFofoca.text = this.fofoca.description
        this.startTimer()
    }

    private fun answer() {
        if (
            (this.fofoca.veracity && this.rbPlayTrue.isChecked) ||
            (!this.fofoca.veracity && this.rbPlayFalse.isChecked)
        )
            setResult(RESULT_OK)

        finish()
    }

    private fun startTimer(){
        Thread{
            while (this.pbPlayTimer.progress < 100){
                this.pbPlayTimer.progress += 1
                Thread.sleep(100)
            }
            finish()
        }.start()
    }
}
