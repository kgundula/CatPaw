package za.co.gundula.app.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.gundula.app.CatPawApplication
import za.co.gundula.app.R
import za.co.gundula.app.viewmodel.CatViewModel
import za.co.gundula.app.viewmodel.CatViewModelFactory

class MainActivity : AppCompatActivity() {

    private val catViewModel: CatViewModel by viewModels {
        CatViewModelFactory((application as CatPawApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_cats)

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(this, 4)
            }
            else -> {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }
        }

        catViewModel.init()
        catViewModel.allCats.observe(this) { cats ->
            val adapter = CatsListRecyclerViewAdapter(cats)
            adapter.getClickedCatSubject().subscribe { cat_id ->
                //open details activity
            }
            recyclerView.adapter = adapter

        }
    }
}