package br.edu.ifpb.rgb

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvPaleta: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private var paleta = Paleta()
    private var formResult: ActivityResultLauncher<Intent>? = null

    init {
        this.paleta.add(Cor("Vermelho", 100, 0, 0))
        this.paleta.add(Cor("Verde", 0, 100, 0))
        this.paleta.add(Cor("Azul", 0, 0, 100))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.rvPaleta = findViewById(R.id.rvMainPaleta)
        this.fabAdd = findViewById(R.id.fabMainAdd)

        this.rvPaleta.adapter = Paletadapter(this.paleta.get(), OnItemClick())
        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvPaleta)

        this.formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK){
                val cor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("COR", Cor::class.java)
                } else {
                    it.data?.getSerializableExtra("COR")
                } as Cor

                if (cor.id == this.paleta.size()) {
                    this.paleta.add(cor)
                    (this.rvPaleta.adapter as Paletadapter).notifyItemInserted(this.paleta.size())
                } else {
                    Log.i("COR", "Id: ${cor.id}" + cor.toString())
                    Log.i("PALETA SIZE", this.paleta.size().toString())
                    val changeCor = this@MainActivity.paleta.get(cor.id)
                    changeCor.name = cor.name
                    changeCor.red = cor.red
                    changeCor.green = cor.green
                    changeCor.blue = cor.blue
                    (this.rvPaleta.adapter as Paletadapter).notifyItemChanged(cor.id)
                }
            }
        }

        this.fabAdd.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            this@MainActivity.formResult?.launch(intent)
        }

    }

    inner class OnItemClick: OnItemCLickRecyclerView {
        override fun onItemClick(position: Int) {
            val intent = Intent(this@MainActivity, FormActivity::class.java).apply {
                putExtra("COR", this@MainActivity.paleta.get(position))
            }
            this@MainActivity.formResult?.launch(intent)
        }
    }

    inner class OnSwipe : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            (this@MainActivity.rvPaleta.adapter as Paletadapter).mov(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            if (direction == ItemTouchHelper.END) {
                val builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Confirmação")
                    setMessage("Deseja excluir a cor?")
                    setPositiveButton("Yes") { _, _ ->
                        (this@MainActivity.rvPaleta.adapter as Paletadapter).delete(position)
                    }
                    setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                        (this@MainActivity.rvPaleta.adapter as Paletadapter).notifyItemChanged(position)
                    }
                }
                builder.create().show()
            }
            if (direction == ItemTouchHelper.START) {
                (this@MainActivity.rvPaleta.adapter as Paletadapter).notifyItemChanged(position)
                val cor = (this@MainActivity.rvPaleta.adapter as Paletadapter).list[position]
                val hexCor = String.format("#%02X%02X%02X", cor.red, cor.green, cor.blue)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, hexCor)
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }

        inner class OnClick(var viewHolder: ViewHolder): DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                (this@MainActivity.rvPaleta.adapter as Paletadapter).delete(viewHolder.adapterPosition)
            }
        }

    }
}
