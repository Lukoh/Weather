package com.goforer.weather

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.goforer.weather.di.helper.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

open class Weather : Application(), LifecycleObserver, Application.ActivityLifecycleCallbacks,
    HasAndroidInjector {
    var activity: Activity? = null

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    internal var isInForeground = false

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    companion object {
        internal var isOnBackground = false
        internal var fromBackground = false

        fun exitApplication(activity: Activity) {
            runCatching {
                activity.finishAndRemoveTask()
                exit()
            }.onFailure {
                exit()
            }
        }

        private fun exit() {
            android.os.Process.killProcess(android.os.Process.myPid())
            System.runFinalizersOnExit(true)
            exitProcess(0)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(Timber.DebugTree())
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerComponentCallbacks(ComponentCallback(this))
        registerActivityLifecycleCallbacks(this)
        AppInjector.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            if (isOnBackground)
                fromBackground = true

            isOnBackground = false
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            isOnBackground = true
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.d("onActivityDestroyed")
    }

    class ComponentCallback(private val app: Weather) : ComponentCallbacks2 {
        override fun onConfigurationChanged(newConfig: Configuration) {}

        override fun onTrimMemory(level: Int) {
            if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
                app.isInForeground = true
            }
        }

        override fun onLowMemory() {
            Timber.d("onLowMemory")
        }
    }
}