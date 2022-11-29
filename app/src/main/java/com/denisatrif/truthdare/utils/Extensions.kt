package com.denisatrif.truthdare.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavHostController

fun Context.setColorToVectorDrawable(drawable: Drawable, color: Int) {
    DrawableCompat.setTint(
        drawable,
        ContextCompat.getColor(
            this,
            color
        )
    )
}

fun NavHostController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
    graph.setStartDestination(route)
}