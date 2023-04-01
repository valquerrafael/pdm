package br.edu.ifpb.chocolates

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var lvMainBasket: ListView
    private lateinit var fabMainAdd: FloatingActionButton
    private var basket = Basket()
    private lateinit var chocoladapter: Chocoladapter

    init {
        this.basket.add(Chocolate("Primeiro", 10))
        this.basket.add(Chocolate("Segundo", 20))
        this.basket.add(Chocolate("Terceiro", 30))
        this.basket.add(Chocolate("Quarto", 40))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.lvMainBasket = findViewById(R.id.lvMainBasket)
        this.fabMainAdd = findViewById(R.id.fabMainAdd)
        this.chocoladapter = Chocoladapter(this, ArrayList(this.basket.get()))

        this.lvMainBasket.adapter = chocoladapter

        val formResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK){
                val chocolate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("CHOCOLATE", Chocolate::class.java)
                } else {
                    it.data?.getSerializableExtra("CHOCOLATE")
                } as Chocolate

                (this.lvMainBasket.adapter as Chocoladapter).add(chocolate)
            }
        }

        this.fabMainAdd.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            formResult.launch(intent)
        }

        this.lvMainBasket.onItemClickListener = OnItemClick()
        this.lvMainBasket.onItemLongClickListener = OnItemLongClick()
    }

    inner class OnItemClick: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val chocolate = this@MainActivity.basket.get(position)
            Toast.makeText(this@MainActivity, chocolate.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    inner class OnItemLongClick: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long,
        ): Boolean {
            this@MainActivity.basket.del(position)
            (this@MainActivity.lvMainBasket.adapter as Chocoladapter).notifyDataSetChanged()
            return true
        }

    }
}