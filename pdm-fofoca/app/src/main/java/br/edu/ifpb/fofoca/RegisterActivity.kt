package br.edu.ifpb.fofoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var etRegisterFofoca: EditText
    private lateinit var rbRegisterTrue: RadioButton
    private lateinit var btnRegisterSave: Button
    private lateinit var btnRegisterCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.etRegisterFofoca = findViewById(R.id.etRegisterFofoca)
        this.rbRegisterTrue = findViewById(R.id.rbRegisterTrue)
        this.btnRegisterSave = findViewById(R.id.btnRegisterSave)
        this.btnRegisterCancel = findViewById(R.id.btnRegisterCancel)

        this.btnRegisterSave.setOnClickListener{ this.finishRegistration() }
        this.btnRegisterCancel.setOnClickListener{ this.cancelRegistration() }
    }

    private fun finishRegistration() {
        val description = this.etRegisterFofoca.text.toString()
        val veracity = this.rbRegisterTrue.isChecked

        if (description.isBlank())
            Toast.makeText(this, "DESCRIÇÃO DE FOFOCA INVÁLIDA", Toast.LENGTH_SHORT).show()
        else {
            val intent = Intent().apply {
                putExtra("FOFOCA", Fofoca(description, veracity))
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun cancelRegistration() {
        finish()
    }

}
