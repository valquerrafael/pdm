package br.edu.ifpb.fofoca

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var btnMainPlay: Button
    private lateinit var btnMainRegister: Button
    private lateinit var tvMainQuantity: TextView
    private var fofoqueiro = Fofoqueiro()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.btnMainPlay = findViewById(R.id.btnMainPlay)
        this.btnMainRegister = findViewById(R.id.btnMainRegister)
        this.tvMainQuantity = findViewById(R.id.tvMainQuantity)

        val playResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK)
                    this.showToastMessage("GANHOU")
                else
                    this.showToastMessage("PERDEU")
            }

        this.btnMainPlay.setOnClickListener {
            if (this.fofoqueiro.getTotalFofocas() > 0) {
                val intent = Intent(this, PlayActivity::class.java).apply {
                    putExtra("FOFOCA", this@MainActivity.fofoqueiro.getRandomFofoca())
                }

                playResult.launch(intent)
            }  else
                this.showToastMessage("NENHUMA FOFOCA CADASTRADA")
        }

        val registerResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val fofoca = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.data?.getSerializableExtra("FOFOCA", Fofoca::class.java)
                    } else {
                        it.data?.getSerializableExtra("FOFOCA")
                    } as Fofoca

                    this.fofoqueiro.addFofoca(fofoca)
                    this.updateLayoutInfo()
                    this.showToastMessage("Fofoca cadastrada")
                }
            }

        this.btnMainRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            registerResult.launch(intent)
        }

        this.updateLayoutInfo()
    }

    private fun updateLayoutInfo() {
        tvMainQuantity.text = "${this.fofoqueiro.getTotalFofocas()} fofoca(s) cadastrada(s)"
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
