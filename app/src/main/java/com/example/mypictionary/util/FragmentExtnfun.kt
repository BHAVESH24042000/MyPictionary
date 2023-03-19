package com.example.mypictionary.util

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(@StringRes res: Int){
    Snackbar.make( requireView(),res, Snackbar.LENGTH_LONG).show()
}