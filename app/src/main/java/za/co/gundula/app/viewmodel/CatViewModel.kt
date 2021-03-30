package za.co.gundula.app.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import za.co.gundula.app.model.Cat
import za.co.gundula.app.model.CatRemoteModel
import za.co.gundula.app.network.CatApi
import za.co.gundula.app.network.NetworkService
import za.co.gundula.app.repository.CatRepository

class CatViewModel(private val repository: CatRepository) : ViewModel() {

    private lateinit var catApi : CatApi
    private var isNetworkEnabled : Boolean = false

    fun init(isConnected: Boolean) {
        catApi = NetworkService.getCatService();
        this.isNetworkEnabled = isConnected
        loadCats()
    }

    private fun loadCats() {
        //if database has no cats objects then do a network call.
        allCats.observeForever(Observer { cats ->
            if (cats.isEmpty() && isNetworkEnabled) {
                fetchCatsPictures()
                println("Database is loaded with new data")
            } else {
                println("Database already loaded with data")
            }
        })

    }

    val allCats: LiveData<List<Cat>> = repository.allCats.asLiveData()

    private fun insert(cat: Cat) = viewModelScope.launch {
        repository.insert(cat)
    }

    private fun fetchCatsPictures() {
        catApi.getCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(this::handleOnError)
            .subscribe(this::handleResponse)
    }

    private fun handleResponse(catsList: List<CatRemoteModel>) {
        var catNumber = 1
        catsList.forEach {
            val catTitle = "Cat $catNumber"
            val catDesciption = "I am Cat $catNumber, a lot of fun to live with, but definitely not the cat for everyone, or for first-time cat owners." +
                    " Extremely intelligent, curious and active, they demand a lot of interaction and woe betide the owner who doesn't provide it."
            val cat = Cat(it.id, it.url, catTitle, catDesciption)
            insert(cat)
            catNumber++
        }
    }

    private fun handleOnError(throwable: Throwable) {
        Log.i("Network error", throwable.localizedMessage)
    }


}

