package br.edu.ifpb.cadastro

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvNames: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private var list = mutableListOf<String>()
    private lateinit var etName: EditText
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvNames = findViewById(R.id.rvNames)
        this.fabAdd = findViewById(R.id.fabAdd)

        this.fabAdd.setOnClickListener { add() }

        this.rvNames.adapter = MyAdapter(this.list)
        (this.rvNames.adapter as MyAdapter).onItemClickRecyclerView = OnItemClick()

        this.tts = TextToSpeech(this, null)

        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvNames)
    }

    fun add(){
        this.etName = EditText(this)
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Novo Nome!")
            setMessage("Digite o novo nome")
            setView(this@MainActivity.etName)
            setPositiveButton("Salvar", OnClick())
            setNegativeButton("Cancelar", null)
        }
        builder.create().show()
    }

    inner class OnClick: OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val name = this@MainActivity.etName.text.toString()
            (this@MainActivity.rvNames.adapter as MyAdapter).add(name)
        }
    }

    inner class OnItemClick: OnItemClickRecyclerView{
        override fun onItemClick(position: Int) {
            val name = this@MainActivity.list[position]
            this@MainActivity.tts.speak(name, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    inner class OnSwipe: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            (this@MainActivity.rvNames.adapter as MyAdapter).change(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (this@MainActivity.rvNames.adapter as MyAdapter).remove(viewHolder.adapterPosition)
        }
    }
}