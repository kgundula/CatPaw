package za.co.gundula.app.ui

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import za.co.gundula.app.CatPawApplication
import za.co.gundula.app.R
import za.co.gundula.app.viewmodel.CatViewModel
import za.co.gundula.app.viewmodel.CatViewModelFactory

class MainActivity : AppCompatActivity() {

    var isConnected = false

    private lateinit var coordinatorLayout : CoordinatorLayout
    private lateinit var recyclerView: RecyclerView

    private val catViewModel: CatViewModel by viewModels {
        CatViewModelFactory((application as CatPawApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        recyclerView = findViewById(R.id.recycler_view_cats)
        coordinatorLayout = findViewById(R.id.coordinator_layout)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_cats)

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(this, 4)
            }
            else -> {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            }
        }

        catViewModel.init(isConnected)
        catViewModel.allCats.observe(this) { cats ->
            if (!isConnected && cats.isEmpty()) {
                networkSnackBar()
            } else {
                val adapter = CatsListRecyclerViewAdapter(cats)
                adapter.getClickedCatSubject().subscribe { cat_id ->
                    startActivity(
                        CatDetailsActivity.getStartIntent(
                            this,
                            cat_id
                        )
                    )
                }
                recyclerView.adapter = adapter
            }
        }


    }

    private fun networkSnackBar() {
        Snackbar.make(coordinatorLayout, getString(R.string.network_toast), Snackbar.LENGTH_INDEFINITE).show()
    }
}