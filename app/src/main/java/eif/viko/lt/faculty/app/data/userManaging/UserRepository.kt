package eif.viko.lt.faculty.app.data.userManaging

interface UserRepository {

    suspend fun insertUser(id: String)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun getUser(id: String): User?
}