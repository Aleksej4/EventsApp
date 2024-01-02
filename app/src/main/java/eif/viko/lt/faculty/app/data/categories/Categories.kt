package eif.viko.lt.faculty.app.data.categories

class Categories {
    private var categories: List<String>?= null

    fun getCategories(): List<String>?{
        return categories
    }

    fun setCategories(){
        val newCategories = listOf("Sports", "Arts", "Festivals", "Community", "Workshops", "Conferences", "Other")
        categories = newCategories
    }
}