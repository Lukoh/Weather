package com.goforer.fitpettest.presentation.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.goforer.fitpettest.di.Injectable
import com.goforer.base.storage.Storage
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment(), Injectable {
    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T

    internal var isLoading = false

    internal lateinit var mainActivity: MainActivity

    private lateinit var context: Context

    private var errorDialogMsg = ""

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private var isFromBackStack = mutableMapOf<String, Boolean>()

    protected open lateinit var navController: NavController

    @Inject
    internal lateinit var storage: Storage

    open fun needTransparentToolbar() = false
    open fun needSystemBarTextWhite() = false

    companion object {
        internal const val FRAGMENT_TAG = "fragment_tag"

        internal const val SEOUL = "Seoul"
        internal const val LONDON = "London"
        internal const val CHICAGO = "Chicago"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = _binding ?: bindingInflater.invoke(inflater, container, false)
        mainActivity = (activity as MainActivity?)!!
        (activity as MainActivity).supportActionBar?.hide()
        navController =
            (mainActivity.supportFragmentManager.fragments.first() as NavHostFragment).navController

        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.context = context
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()

        if (::onBackPressedCallback.isInitialized) {
            onBackPressedCallback.isEnabled = false
            onBackPressedCallback.remove()
        }
    }

    override fun getContext() = context

    open suspend fun doOnFromBackground() {
    }

    protected open fun handleBackPressed() {
    }

    protected fun isNavControllerInitialized() = ::navController.isInitialized

    protected fun setDarkMode(state: Boolean) {
        /*
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

         */

        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_YES
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            resources.configuration.uiMode = Configuration.UI_MODE_NIGHT_NO
        }
    }
}