package com.salti.crud_berita_user.model

import java.io.Serializable

data class ModelUser(
    val id : Int,
    val username : String,
    val fullname : String,
    val email : String,
    val tgl_daftar : String
) : Serializable
