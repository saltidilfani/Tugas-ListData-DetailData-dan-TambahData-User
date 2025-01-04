package com.salti.crud_berita_user.model

data class UserResponse(
    val success: Boolean,
    val data: List<ModelUser>
)
