package com.denisatrif.truthdare.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

fun Context.setColorToVectorDrawable(drawable: Drawable, color: Int) {
    DrawableCompat.setTint(
        drawable,
        ContextCompat.getColor(
            this,
            color
        )
    )
}

@RootNavGraph(start = true)
@NavGraph
annotation class SettingsNavGraph(
    val start: Boolean = false
)

fun DestinationsNavigator.navigateAndClean(route: String) {
//    navigate(route = route) {
//        popUpTo(graph.startDestinationId) { inclusive = true }
//    }
//    graph.setStartDestination(route)
}
