package za.co.gundula.app

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import za.co.gundula.app.database.CatRoomDatabase
import za.co.gundula.app.repository.CatRepository

class CatPawApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CatRoomDatabase.getDatabase(this) }
    val repository by lazy { CatRepository(database.catDao()) }
}