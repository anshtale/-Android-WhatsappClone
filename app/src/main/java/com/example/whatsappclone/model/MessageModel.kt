package com.example.whatsappclone.model

import java.sql.Timestamp

data class MessageModel(
    val message:String? = "",
    val senderId: String? = "",
    val timestamp: Long? = 0

)
