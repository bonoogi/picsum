package com.bonoogi.picsum.scenes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bonoogi.picsum.R
import com.bonoogi.picsum.scenes.detail.DetailFragment
import com.bonoogi.picsum.scenes.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */

interface ImageNavigator {
    fun navigateToImage(id: String)
}

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), ImageNavigator {

    companion object {
        private const val LIST_TAG = "LIST_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = ListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_view, fragment, LIST_TAG)
                .addToBackStack(LIST_TAG)
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    override fun navigateToImage(id: String) {
        val fragment = DetailFragment.newInstance(id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_view, fragment, id)
            .addToBackStack(id)
            .commit()
    }
}