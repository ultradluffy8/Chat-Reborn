package com.example.chatreborn

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context : Context , val userList : ArrayList<user>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.name.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name :TextView = itemView.findViewById(R.id.txt_user_name)

    }


}