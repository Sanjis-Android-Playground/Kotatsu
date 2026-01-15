package org.koitharu.kotatsu.core.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

import org.koitharu.kotatsu.core.ui.DefaultActivityLifecycleCallbacks

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AcraScreenLogger @Inject constructor() : FragmentLifecycleCallbacks(), DefaultActivityLifecycleCallbacks {

	override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
		super.onFragmentAttached(fm, f, context)
	}

	override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
		super.onFragmentDetached(fm, f)
	}

	override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
		super.onActivityCreated(activity, savedInstanceState)
		(activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(this, true)
	}

	override fun onActivityDestroyed(activity: Activity) {
		super.onActivityDestroyed(activity)
	}
}
