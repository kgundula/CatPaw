package za.co.gundula.app.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import za.co.gundula.app.database.CatDao
import za.co.gundula.app.model.Cat

class CatRepository(private val catDao: CatDao) {

    val allCats: Flow<List<Cat>> = catDao.getCats()

    fun getCat(catId: String): Flow<Cat> = catDao.getCat(catId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cat: Cat) {
        catDao.insert(cat)
    }
}