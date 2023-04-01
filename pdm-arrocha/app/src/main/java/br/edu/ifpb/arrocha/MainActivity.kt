package br.edu.ifpb.arrocha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var tvMinLimit: TextView
    private lateinit var tvMaxLimit: TextView
    private lateinit var etGuess: EditText
    private lateinit var btGuess: Button
    private lateinit var tvStatus: TextView
    private lateinit var btReset: Button
    private lateinit var arrocha: Arrocha

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvMinLimit = findViewById(R.id.tvMinLimit)
        this.tvMaxLimit = findViewById(R.id.tvMaxLimit)
        this.etGuess = findViewById(R.id.etGuess)
        this.btGuess = findViewById(R.id.btGuess)
        this.tvStatus = findViewById(R.id.tvStatus)
        this.btReset = findViewById(R.id.btReset)
        this.arrocha = Arrocha(0, 100)

        this.updateLayoutInfo()

        this.btGuess.setOnClickListener {
            this.guess()
        }

        this.btReset.setOnClickListener {
            this.reset()
        }
    }

    private fun reset() {
        this.arrocha = Arrocha(0, 100)
        this.updateLayoutInfo()
    }

    private fun guess() {
        val guess = this.etGuess.text.toString()

        if (guess != "")
            this.arrocha.guess(Integer.parseInt(guess))

        this.updateLayoutInfo()

        Toast.makeText(
            this,
            this.arrocha.getGuessStatus().toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateLayoutInfo() {
        this.etGuess.setText("")
        this.tvMinLimit.text = this.arrocha.getMinLimit().toString()
        this.tvMaxLimit.text = this.arrocha.getMaxLimit().toString()
        this.tvStatus.text = this.arrocha.getStatus().toString()
    }
}
