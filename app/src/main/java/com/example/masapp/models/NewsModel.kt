package com.example.masapp.models

import android.annotation.SuppressLint
import java.io.Serializable
import java.sql.Date
import java.text.SimpleDateFormat

/*
///
/// Project: MasApp
/// Created by pc on 12/19/2021.
///
*/
class NewsModel(
    val id: Long,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val content: String,
    val createdDate: String
): Serializable {
    @SuppressLint("SimpleDateFormat")
    fun convertTime(): String{
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss")
        val netDate = Date(createdDate.toLong())
        return sdf.format(netDate)
    }

    override fun toString(): String {
        return "NewsModel(title='$title', thumbnail='$thumbnail', shortDescription='$shortDescription', content='$content', createdDate='$createdDate')"
    }

}