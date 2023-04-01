package br.edu.ifpb.chocolates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Chocoladapter(var context: Context, var chocolates: ArrayList<Chocolate>): BaseAdapter() {

    fun add(chocolate: Chocolate) {
        this.chocolates.add(chocolate)
    }

    fun del(chocolate: Chocolate) {
        this.chocolates.remove(chocolate)
    }

    override fun getCount(): Int {
        return this.chocolates.size
    }

    override fun getItem(position: Int): Any {
        return this.chocolates[position]
    }

    override fun getItemId(position: Int): Long {
        return this.chocolates[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView == null) {
            LayoutInflater.from(context).inflate(R.layout.activity_chocolactivity, parent, false)
        }else{
            convertView
        }

        val tvQuantity = view.findViewById<TextView>(R.id.tvChocolatactivityQuantity)
        val tvDescription = view.findViewById<TextView>(R.id.tvChocolatactivityDescription)
        val chocolate = this.chocolates[position]

        tvQuantity.text = chocolate.quantity.toString()
        tvDescription.text = chocolate.description
        return view
    }
}