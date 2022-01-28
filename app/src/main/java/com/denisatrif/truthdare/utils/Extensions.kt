package com.denisatrif.truthdare.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Context.setColorToVectorDrawable(drawble: Drawable, color: Int) {
    DrawableCompat.setTint(
        drawble,
        ContextCompat.getColor(
            this,
            color
        )
    )
}