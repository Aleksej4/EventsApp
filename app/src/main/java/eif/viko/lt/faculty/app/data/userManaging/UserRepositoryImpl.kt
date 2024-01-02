package eif.viko.lt.faculty.app.data.userManaging

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl(
    private val dao: UserDao,
    private val database: FirebaseDatabase
): UserRepository {

    override suspend fun insertUser(id: String) {
        val usersRef = database.getReference("users")
        val userRef = usersRef.child(id)

        val user = suspendCoroutine { continuation ->
            userRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userSnapshot: DataSnapshot? = task.result

                    val firstName = userSnapshot?.child("firstName")?.getValue(String::class.java) ?: ""
                    val lastName = userSnapshot?.child("lastName")?.getValue(String::class.java) ?: ""

                    val user = User(firstName, lastName, id)
                    continuation.resume(user)
                }
            }
        }

        user.let { dao.insertUser(it) }
    }

    override suspend fun updateUser(user: User) {
        val usersRef = database.getReference("users")
        val userRef = usersRef.child(user.id)

        val userData = suspendCoroutine<Unit> { continuation ->
            val updatedUserData = hashMapOf<String, Any>(
                "firstName" to user.firstName,
                "lastName" to user.lastName
            )
            userRef.updateChildren(updatedUserData)

        }
        dao.insertUser(user = user)
    }

    override suspend fun deleteUser(user: User) {
        dao.deleteUser(user) //after log out delete user
    }

    override suspend fun getUser(id: String): User? {
        return dao.getUser(id)
    }
}