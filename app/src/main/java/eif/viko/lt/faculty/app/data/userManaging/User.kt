package eif.viko.lt.faculty.app.data.userManaging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    var firstName: String,
    var lastName: String,
    @PrimaryKey val id: String
)