package eif.viko.lt.faculty.app.data

import com.google.firebase.database.Exclude


data class Event (
    val title: String,
    val description: String,
    val category: String,
    val date: String,
    var image: String? = null,
    val userId: String,

    @get:Exclude
    var id: String? = null
)