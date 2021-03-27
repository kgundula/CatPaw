package za.co.gundula.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import za.co.gundula.app.repository.CatRepository

class CatDetailViewModelFactory(private val repository: CatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}