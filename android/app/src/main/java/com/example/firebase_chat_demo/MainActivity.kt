package com.example.firebase_chat_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        db = FirebaseDatabase.getInstance().getReference("chat/messages")

        listView = findViewById(R.id.messageList)
        val msgInput: EditText = findViewById(R.id.messageInput)
        val usernameInput: EditText = findViewById(R.id.usernameInput)
        val sendBtn: Button = findViewById(R.id.sendButton)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        // Poslusanje
        db.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, prev: String?) {
                val msg = snapshot.getValue(Message::class.java)
                msg?.let {
                    val text = "[${it.time}] ${it.user}: ${it.text}"
                    adapter.add(text)
                    listView.smoothScrollToPosition(adapter.count - 1)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        // Posiljanje
        sendBtn.setOnClickListener {
            val text = msgInput.text.toString().trim()
            val user = usernameInput.text.toString().trim().ifEmpty { "Anonimni uporabnik" }

            if (text.isNotEmpty()) {
                val message = Message(
                    text = text,
                    time = java.text.SimpleDateFormat("HH:mm:ss").format(java.util.Date()),
                    user = user
                )

                db.push().setValue(message)
                    .addOnFailureListener {
                        Toast.makeText(this, "Napaka: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                msgInput.setText("")
            }
        }
    }
}

data class Message(
    val text: String = "",
    val time: String = "",
    val user: String = ""
)
