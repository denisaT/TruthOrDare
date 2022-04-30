package com.denisatrif.truthdare.utils

import android.os.Looper

object GenericUtils {

    fun isMainThread(): Boolean =
        Looper.myLooper() == Looper.getMainLooper()

}