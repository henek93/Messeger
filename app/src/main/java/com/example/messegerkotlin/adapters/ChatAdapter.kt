package com.example.messegerkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messegerkotlin.Messege
import com.example.messegerkotlin.R

class ChatAdapter(private val currentId: String?, outherId: String?) : RecyclerView.Adapter<ChatAdapter.MyHolder>() {

    companion object {
        private const val MY_MESSEGE = 100
        private const val OUTHER_MESSEGE = 101
    }

    var messeges: List<Messege?> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(messege: Messege?) {
            val text = itemView.findViewById<TextView>(R.id.textViewMessege)
            text.text = messege?.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.MyHolder {
        var resId = 0
        if (viewType == MY_MESSEGE) {
            resId = R.layout.my_messege_item
        } else {
            resId = R.layout.outher_messege_item
        }
        return MyHolder(LayoutInflater.from(parent.context).inflate(resId, parent, false))
    }

    override fun onBindViewHolder(holder: ChatAdapter.MyHolder, position: Int) {
        val messege = messeges[position]
        holder.bind(messege)
    }

    override fun getItemCount(): Int {
        return messeges.size
    }

    override fun getItemViewType(position: Int): Int {
        val messege = messeges[position]
        if (messege?.sendertId.equals(currentId)) {
            return MY_MESSEGE
        } else {
            return OUTHER_MESSEGE
        }
    }
}