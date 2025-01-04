package com.salti.crud_berita_user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.salti.crud_berita_user.adapter.UserAdapter
import com.salti.crud_berita_user.model.ModelUser
import com.salti.crud_berita_user.model.UserResponse
import com.salti.crud_berita_user.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_user)

        // Inisialisasi view
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.rv_dataUser)

        // Inisialisasi adapter dengan daftar kosong
        userAdapter = UserAdapter(emptyList()) { modelUser: ModelUser -> userOnClick(modelUser) }
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Swipe refresh untuk memuat ulang data
        swipeRefreshLayout.setOnRefreshListener {
            getUser()
        }

        // Ambil data pengguna dari server
        getUser()
    }

    private fun userOnClick(modelUser: ModelUser) {
        // Navigasi ke halaman detail pengguna
        val intent = Intent(this, DetailUserActivity::class.java).apply {
            putExtra("username", modelUser.username)
            putExtra("fullname", modelUser.fullname)
            putExtra("email", modelUser.email)
            putExtra("tgl_daftar", modelUser.tgl_daftar)
        }
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUser() {
        swipeRefreshLayout.isRefreshing = true
        val call = ApiClient.retrofit.getAllUsers()
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    userAdapter.userList = response.body()?.data ?: emptyList()
                    userAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@ListUserActivity, "Gagal memuat data!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@ListUserActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
