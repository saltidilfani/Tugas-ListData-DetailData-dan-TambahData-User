package com.salti.crud_berita_user

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DetailUserActivity : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var fullname: TextView
    private lateinit var email: TextView
    private lateinit var tanggalDaftar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_user)

        // Hubungkan dengan View
        username = findViewById(R.id.Username)
        fullname = findViewById(R.id.Fullname)
        email = findViewById(R.id.Email)
        tanggalDaftar = findViewById(R.id.TanggalDaftar)

        username.text = intent.getStringExtra("username")
        fullname.text = intent.getStringExtra("fullname")
        email.text = intent.getStringExtra("email")
        tanggalDaftar.text = intent.getStringExtra("tgl_daftar")

    }
}