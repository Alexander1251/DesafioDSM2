package com.example.meerkat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateStudent: Button
    private lateinit var btnStudentList: Button
    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        btnCreateStudent = findViewById(R.id.btnCreateStudent)
        btnStudentList = findViewById(R.id.btnStudentList)
        btnLogout = findViewById(R.id.btnLogout)

        btnCreateStudent.setOnClickListener {
            startActivity(Intent(this, CreateStudentActivity::class.java))
        }

        btnStudentList.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
