package com.salti.crud_berita_user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salti.crud_berita_user.R
import com.salti.crud_berita_user.model.ModelUser

class UserAdapter(
    var userList: List<ModelUser>, // Gunakan var agar daftar bisa diperbarui
    private val onClick: (ModelUser) -> Unit // Lambda untuk menangani klik item
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder untuk memegang view item
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.Username)
        val fullname: TextView = itemView.findViewById(R.id.Fullname)
        val email: TextView = itemView.findViewById(R.id.Email)
        val tanggalDaftar: TextView = itemView.findViewById(R.id.TanggalDaftar)
    }

    // Membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_data_user, parent, false)
        return UserViewHolder(view)
    }

    // Menghubungkan data ke ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.username.text = user.username
        holder.fullname.text = user.fullname
        holder.email.text = user.email
        holder.tanggalDaftar.text = user.tgl_daftar

        holder.itemView.setOnClickListener { onClick(user) }
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int = userList.size
}
