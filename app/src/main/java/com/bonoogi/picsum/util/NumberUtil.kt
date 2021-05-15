package com.bonoogi.picsum.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
val Int.px: Int get() = pxFloat.toInt()
val Float.px: Int get() = pxFloat.toInt()
fun Int.toPx(): Int {
    return this.px
}

val Int.dpValue: Int get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

val Int.pxFloat: Float get() = (this * Resources.getSystem().displayMetrics.density)
val Float.pxFloat: Float get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.sp: Int get() = (this / Resources.getSystem().displayMetrics.scaledDensity).toInt()
val Int.spToPx: Float get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

