package com.bonoogi.picsum.data

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
data class PagingList<T>(
    private val _list: List<T>,
    val hasPrev: Boolean,
    val hasNext: Boolean,
    val currentPage: Int
): List<T> by _list