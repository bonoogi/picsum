package com.bonoogi.picsum.scenes.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    val handle: SavedStateHandle
) : ViewModel() {
    
}