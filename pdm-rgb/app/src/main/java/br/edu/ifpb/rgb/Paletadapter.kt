package br.edu.ifpb.rgb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class Paletadapter(val list: MutableList<Cor>, val onItemCLick: OnItemCLickRecyclerView) : RecyclerView.Adapter<Paletadapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Paletadapter.MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false
        )
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: Paletadapter.MyHolder, position: Int) {
        val cor = this.list[position]
        holder.tvItem?.text = cor.toString()
    }

    override fun getItemCount(): Int = this.list.size

    fun delete(position: Int){
        this.list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.list.size)
    }

    fun mov(from: Int, to: Int){
        Collections.swap(this.list, from, to)
        notifyItemMoved(from, to)
    }

    inner class MyHolder(item: View): RecyclerView.ViewHolder(item){
        var tvItem: TextView?

        init {
            this.tvItem = item.findViewById(R.id.tvItem)
            itemView.setOnClickListener{
                this@Paletadapter.onItemCLick.onItemClick(this.adapterPosition)
            }
        }
    }
}
