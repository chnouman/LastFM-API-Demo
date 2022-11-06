package com.chnouman.lastfmapidemo.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.chnouman.lastfmapidemo.core.util.Inflate
/**
 * To avoid boiler plate code, this will help inflate the UI for Activities
 * We will avoid adding any other functionality to these classes instead will utilise
 * Delegates for shared functionality
 * */
abstract class BaseActivity<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    AppCompatActivity() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewDataBinding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initUserInterface()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun initUserInterface()
}
