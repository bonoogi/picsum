package com.bonoogi.picsum.data

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
data class PagingList<T>(
    private val _list: List<T>,
    val currentPage: Int,
    val hasPrev: Boolean = false,
    val hasNext: Boolean = false
): List<T> by _list