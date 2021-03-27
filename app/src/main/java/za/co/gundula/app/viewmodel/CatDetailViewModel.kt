package za.co.gundula.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import za.co.gundula.app.repository.CatRepository

class CatDetailViewModel (private val catRepository: CatRepository) : ViewModel() {
    fun getCat(catId: String) = catRepository.getCat(catId).asLiveData()
}