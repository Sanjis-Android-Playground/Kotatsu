package org.koitharu.kotatsu.stats.domain

import android.app.Application
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koitharu.kotatsu.core.prefs.AppSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageCollector @Inject constructor(
	@ApplicationContext private val context: Context,
	private val settings: AppSettings,
) : DefaultLifecycleObserver {

	private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
	private var sessionStartTime: Long = 0

	fun init() {
		ProcessLifecycleOwner.get().lifecycle.addObserver(this)
	}

	override fun onStart(owner: LifecycleOwner) {
		sessionStartTime = System.currentTimeMillis()
	}

	override fun onStop(owner: LifecycleOwner) {
		if (sessionStartTime > 0) {
			val sessionDuration = System.currentTimeMillis() - sessionStartTime
			scope.launch {
				recordUsage(sessionDuration)
			}
			sessionStartTime = 0
		}
	}

	private fun recordUsage(duration: Long) {
		val prefs = context.getSharedPreferences("app_usage_stats", Context.MODE_PRIVATE)
		val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24) // Day number
		
		// Daily stats
		val dailyKey = "usage_day_$today"
		val dailyTotal = prefs.getLong(dailyKey, 0L)
		prefs.edit().putLong(dailyKey, dailyTotal + duration).apply()
		
		// Weekly stats (aggregate)
		val weeklyKey = "usage_week_${today / 7}"
		val weeklyTotal = prefs.getLong(weeklyKey, 0L)
		prefs.edit().putLong(weeklyKey, weeklyTotal + duration).apply()
		
		// Monthly stats (aggregate)
		val monthlyKey = "usage_month_${today / 30}"
		val monthlyTotal = prefs.getLong(monthlyKey, 0L)
		prefs.edit().putLong(monthlyKey, monthlyTotal + duration).apply()
		
		// Clean up old data (keep last 90 days)
		cleanOldData(prefs, today)
	}

	private fun cleanOldData(prefs: android.content.SharedPreferences, currentDay: Long) {
		val editor = prefs.edit()
		prefs.all.keys.forEach { key ->
			if (key.startsWith("usage_day_")) {
				val day = key.substringAfter("usage_day_").toLongOrNull()
				if (day != null && currentDay - day > 90) {
					editor.remove(key)
				}
			}
		}
		editor.apply()
	}

	fun getDailyUsage(days: Int = 7): Map<Long, Long> {
		val prefs = context.getSharedPreferences("app_usage_stats", Context.MODE_PRIVATE)
		val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
		val result = mutableMapOf<Long, Long>()
		
		for (i in 0 until days) {
			val day = today - i
			val key = "usage_day_$day"
			val usage = prefs.getLong(key, 0L)
			result[day] = usage
		}
		
		return result
	}

	fun getWeeklyUsage(weeks: Int = 4): Map<Long, Long> {
		val prefs = context.getSharedPreferences("app_usage_stats", Context.MODE_PRIVATE)
		val currentWeek = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)) / 7
		val result = mutableMapOf<Long, Long>()
		
		for (i in 0 until weeks) {
			val week = currentWeek - i
			val key = "usage_week_$week"
			val usage = prefs.getLong(key, 0L)
			result[week] = usage
		}
		
		return result
	}

	fun getMonthlyUsage(months: Int = 3): Map<Long, Long> {
		val prefs = context.getSharedPreferences("app_usage_stats", Context.MODE_PRIVATE)
		val currentMonth = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)) / 30
		val result = mutableMapOf<Long, Long>()
		
		for (i in 0 until months) {
			val month = currentMonth - i
			val key = "usage_month_$month"
			val usage = prefs.getLong(key, 0L)
			result[month] = usage
		}
		
		return result
	}
}
