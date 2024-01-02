package eif.viko.lt.faculty.app.presentation.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eif.viko.lt.faculty.app.data.categories.Categories
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private var categories: Categories
): ViewModel() {
    var categoriesList = categories.getCategories()
}