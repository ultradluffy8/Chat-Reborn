package com.example.chatreborn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var userRecyckerView: RecyclerView
    lateinit var userList: ArrayList<user>
    lateinit var adapter: UserAdapter
    lateinit var auth: FirebaseAuth
    lateinit var DbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        userRecyckerView = findViewById(R.id.user_recycler_view)
        userRecyckerView.layoutManager = LinearLayoutManager(this)
        userRecyckerView.adapter = adapter
        DbRef = FirebaseDatabase.getInstance().getReference()
        DbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(x in snapshot.children){
                    val currentUser = x.getValue(user::class.java)
                    userList.add(currentUser!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout)
            auth.signOut()
        val intent = Intent(this, LoginReborn::class.java)
        finish()
        startActivity(intent)
        return true

    }
}