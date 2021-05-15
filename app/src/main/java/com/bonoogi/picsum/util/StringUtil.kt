package com.bonoogi.picsum.util

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */

fun String.splitAndTrim(delimeter: String): List<String> = split(delimeter).map(String::trim)