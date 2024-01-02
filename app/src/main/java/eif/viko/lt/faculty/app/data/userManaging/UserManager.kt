package eif.viko.lt.faculty.app.data.userManaging

class UserManager {
    private var userId: String? = null

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(newId: String){
        userId = newId
    }
}