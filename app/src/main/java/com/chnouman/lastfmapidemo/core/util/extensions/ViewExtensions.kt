package com.chnouman.lastfmapidemo.core.util.extensions

import android.view.View

fun View.hide() = this.apply { visibility = View.GONE }
fun View.show() = this.apply { visibility = View.VISIBLE }