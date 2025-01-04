package com.salti.crud_berita_user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.salti.crud_berita_user.adapter.BeritaAdapter
import com.salti.crud_berita_user.model.ModelBerita
import com.salti.crud_berita_user.model.ResponseBerita
import com.salti.crud_berita_user.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<ResponseBerita>
    private lateinit var beritaAdapter: BeritaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.rv_berita)

        beritaAdapter = BeritaAdapter { modelBerita: ModelBerita -> beritaOnClick(modelBerita)  }
        recyclerView.adapter = beritaAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.VERTICAL,
            false
        )
        //swipe refresh
        swipeRefreshLayout.setOnClickListener {
            getData()
        }
        getData()

        val btnTambahUser: Button = findViewById(R.id.btnTambahDataUser)
        btnTambahUser.setOnClickListener {
            val intent = Intent(this@MainActivity, ListUserActivity::class.java)
            startActivity(intent)
        }


    }

    private fun beritaOnClick(modelBerita: ModelBerita) {
        val intent = Intent(this, DetailBerita::class.java).apply {
            putExtra("gambar", modelBerita.gambar_berita)
            putExtra("judul", modelBerita.judul)
            putExtra("isi", modelBerita.isi_berita)
            putExtra("tanggal", modelBerita.tgl_berita)
        }
        startActivity(intent)
    }

     private fun getData() {
        //proses untuk dapatkan respons
        swipeRefreshLayout.isRefreshing = true
        call = ApiClient.retrofit.getAllBerita()
        call.enqueue(object: Callback<ResponseBerita> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseBerita>,
                response: Response<ResponseBerita>
            ) {
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    beritaAdapter.submitList(response.body()?.data)
                    beritaAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(
                    applicationContext,
                    t.localizedMessage, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}