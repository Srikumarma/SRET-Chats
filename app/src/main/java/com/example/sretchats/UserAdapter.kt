package com.example.sretchats

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentuser =userList[position]

        holder.textname.text = currentuser.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid", currentuser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textname = itemView.findViewById<TextView>(R.id.txtname)

    }
}