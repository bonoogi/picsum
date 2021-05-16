package com.bonoogi.picsum.util

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
object ConstraintLayoutBindingAdapter {
    /**
     * @BindingAdapter("layout_constraintDimensionRatio")
    public static void setConstraintDimensionRatio(View view, String ratio){
    if(view.getParent() instanceof ConstraintLayout){
    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
    layoutParams.dimensionRatio = ratio;
    view.setLayoutParams(layoutParams);
    }
    }
     */
    @JvmStatic
    @BindingAdapter("layout_constraintDimensionRatio")
    fun bindConstraintDimensionRatio(view: View, ratio: String?) {
        val nonNilRatio = ratio ?: return
        val params = view.layoutParams as? ConstraintLayout.LayoutParams ?: return
        params.dimensionRatio = nonNilRatio
        view.layoutParams = params
    }
}