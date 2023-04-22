package br.edu.ifpb.rgb

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts

class FormActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etRed: EditText
    private lateinit var etGreen: EditText
    private lateinit var etBlue: EditText
    private lateinit var btSave: Button
    private lateinit var btCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.etName = findViewById(R.id.etFormName)
        this.etRed = findViewById(R.id.etFormRed)
        this.etGreen = findViewById(R.id.etFormGreen)
        this.etBlue = findViewById(R.id.etFormBlue)
        this.btSave = findViewById(R.id.btFormSave)
        this.btCancel = findViewById(R.id.btFormCancel)

        val cor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("COR", Cor::class.java)
        } else {
            intent.getSerializableExtra("COR")
        } as? Cor

        this.etName.setText(cor?.name)
        this.etRed.setText(cor?.red?.toString())
        this.etGreen.setText(cor?.green?.toString())
        this.etBlue.setText(cor?.blue?.toString())

        this.btSave.setOnClickListener { save(cor) }
        this.btCancel.setOnClickListener { cancel() }
    }

    private fun save(cor: Cor?) {
        val name = this.etName.text.toString()
        val red = this.etRed.text.toString().toInt()
        val green = this.etGreen.text.toString().toInt()
        val blue = this.etBlue.text.toString().toInt()
        var newOrUpdatedCor: Cor

        if (cor == null)
            newOrUpdatedCor = Cor(name, red, green, blue)
        else {
            newOrUpdatedCor = cor
            newOrUpdatedCor.name = name
            newOrUpdatedCor.red = red
            newOrUpdatedCor.green = green
            newOrUpdatedCor.blue = blue
        }

        val intent = Intent().apply {
            putExtra("COR", cor)
            putExtra("NAME", name)
            putExtra("RED", red)
            putExtra("GREEN", green)
            putExtra("BLUE", blue)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun cancel() {
        finish()
    }
}
