package br.edu.ifpb.sorteio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var tvSorteado: TextView
    private lateinit var etText: EditText
    private lateinit var btSave: Button
    private lateinit var btSortear: Button
    private lateinit var sorteador: Sorteador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvSorteado = findViewById(R.id.tvSorteado)
        this.etText = findViewById(R.id.etText)
        this.btSave = findViewById(R.id.btSave)
        this.btSortear = findViewById(R.id.btSortear)
        this.sorteador = Sorteador()

        this.btSave.setOnClickListener {
            this.addText()
        }

        this.btSortear.setOnClickListener {
            this.sortear()
        }
    }

    private fun addText() {
        val text = this.etText.text.toString()

        if (text.isEmpty())
            Toast.makeText(this, "Digite um texto", Toast.LENGTH_SHORT).show()
        else
            this.sorteador.addText(text)

        this.etText.setText("")
    }

    private fun sortear() {
        val text = this.sorteador.sorteio()

        if (text.isNullOrEmpty())
            Toast.makeText(this, "Nenhum texto cadastrado", Toast.LENGTH_SHORT).show()
        else
            this.tvSorteado.text = text

        this.etText.setText("")
    }
}
