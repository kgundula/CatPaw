package za.co.gundula.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import za.co.gundula.app.CatPawApplication
import za.co.gundula.app.GlideApp
import za.co.gundula.app.R
import za.co.gundula.app.viewmodel.CatDetailViewModel
import za.co.gundula.app.viewmodel.CatDetailViewModelFactory

class CatDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CAT_ID = "EXTRA_CAT_ID"

        @JvmStatic
        fun getStartIntent(context: Context, id: String): Intent {
            val intent = Intent(context, CatDetailsActivity::class.java)
            intent.putExtra(EXTRA_CAT_ID, id)
            return intent
        }
    }

    private val catDetailViewModel: CatDetailViewModel by viewModels {
        CatDetailViewModelFactory((application as CatPawApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_details)

        val bundle: Bundle? = intent.extras
        val catId = bundle?.get(EXTRA_CAT_ID) as String
        catDetailViewModel.getCat(catId).observe(this) { cat ->

            val catImage = findViewById<AppCompatImageView>(R.id.cat_image)
            GlideApp.with(catImage)
                .load(cat.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(catImage)

            val catTitle = findViewById<AppCompatTextView>(R.id.cat_title)
            catTitle.text = cat.title

            val catDescription = findViewById<AppCompatTextView>(R.id.cat_description)
            catDescription.text = cat.description

        }

    }

}