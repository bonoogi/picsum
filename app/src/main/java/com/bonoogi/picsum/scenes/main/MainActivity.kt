package com.bonoogi.picsum.scenes.main

import androidx.activity.viewModels
import com.bonoogi.picsum.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@AndroidEntryPoint
class MainActivity: BaseActivity() {
    private val viewModel by viewModels<MainViewModel>()
}