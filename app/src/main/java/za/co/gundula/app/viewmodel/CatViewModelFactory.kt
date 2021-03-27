package za.co.gundula.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import za.co.gundula.app.repository.CatRepository

class CatViewModelFactory (private val repository: CatRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}