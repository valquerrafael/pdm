package br.edu.ifpb.chocolates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FormActivity : AppCompatActivity() {
    private lateinit var etFormDescription: EditText
    private lateinit var etFormQuantity: EditText
    private lateinit var btFormSave: Button
    private lateinit var btFormCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.etFormDescription = findViewById(R.id.etFormDescription)
        this.etFormQuantity = findViewById(R.id.etFormQuantity)
        this.btFormSave = findViewById(R.id.btFormSave)
        this.btFormCancel = findViewById(R.id.btFormCancel)

        this.btFormSave.setOnClickListener { this.save() }
        this.btFormCancel.setOnClickListener { finish() }
    }

    private fun save() {
        val description = this.etFormDescription.text.toString()
        val quantity = this.etFormQuantity.text.toString()

        if (description.isBlank() || quantity.isBlank())
            Toast.makeText(this, "DESCRIÇÃO E/OU QUANTIDADE INVÁLIDA(S)", Toast.LENGTH_SHORT).show()
        else {
            val chocolate = Chocolate(description, quantity.toInt())
            val intent = Intent().apply {
                putExtra("CHOCOLATE", chocolate)
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
