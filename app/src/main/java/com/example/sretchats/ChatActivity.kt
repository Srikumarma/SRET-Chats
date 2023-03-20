package com.example.sretchats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var messageAdapter: MessageAdapter
        var messageList: ArrayList<Message>

        var receiverRoom: String? = null
        var senderRoom: String? = null

        var name = intent.getStringExtra("name")
        var receiverUid = intent.getStringExtra("uid")

        var senderUid = FirebaseAuth.getInstance().uid


        var mDbRef: DatabaseReference

        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)

        var messageBox = findViewById<EditText>(R.id.messageBox)
        var sendButton = findViewById<ImageView>(R.id.send_img)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //logic for adding data to recyclerView

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSanapshot in snapshot.children){

                        val message = postSanapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            } )

        sendButton.setOnClickListener{
            var message = messageBox.text.toString()
            var messageObject = Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
                messageBox.setText("")


        }




    }
}