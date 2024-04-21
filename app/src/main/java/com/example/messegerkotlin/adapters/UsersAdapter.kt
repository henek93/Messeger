package com.example.messegerkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messegerkotlin.R
import com.example.messegerkotlin.User
import com.example.messegerkotlin.databinding.UserItemBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyHolder>() {

    var users: List<User> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onUserClickListener: OnUserClickListener? = null

    class MyHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                textViewUserInfo.text =
                    String.format("%s %s %s", user.name, user.lastName, user.age)

                var resId = 0
                if (user.online) {
                    resId = R.drawable.circle_green
                } else
                    resId = R.drawable.circle_red

                viewStatus.setBackgroundResource(resId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.MyHolder {
        return MyHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UsersAdapter.MyHolder, position: Int) {
        val user = users[position]

        holder.bind(user)

        holder.itemView.setOnClickListener {
            if (onUserClickListener != null) {
                onUserClickListener?.onUserClick(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface OnUserClickListener {
        fun onUserClick(user: User)
    }
}