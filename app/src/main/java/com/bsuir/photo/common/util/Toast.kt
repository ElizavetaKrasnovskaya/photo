package com.bsuir.photo.common.util

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    //TODO custom toast
    toast.show()
}