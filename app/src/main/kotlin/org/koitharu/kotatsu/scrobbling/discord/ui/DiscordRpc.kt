package org.koitharu.kotatsu.scrobbling.discord.ui

import android.content.Context
import dagger.hilt.android.ViewModelLifecycle
import dagger.hilt.android.lifecycle.RetainedLifecycle
import dagger.hilt.android.scopes.ViewModelScoped
import org.koitharu.kotatsu.core.LocalizedAppContext
import org.koitharu.kotatsu.core.prefs.AppSettings
import org.koitharu.kotatsu.parsers.model.Manga
import org.koitharu.kotatsu.reader.ui.pager.ReaderUiState
import org.koitharu.kotatsu.scrobbling.discord.data.DiscordRepository
import javax.inject.Inject

@ViewModelScoped
class DiscordRpc @Inject constructor(
	@LocalizedAppContext private val context: Context,
	private val settings: AppSettings,
	private val repository: DiscordRepository,
	lifecycle: ViewModelLifecycle,
) : RetainedLifecycle.OnClearedListener {

	init {
		lifecycle.addOnClearedListener(this)
	}

	override fun onCleared() {
		// no-op
	}

	fun clearRpc() {
		// no-op
	}

	fun setIdle() {
		// no-op
	}

	fun updateRpc(manga: Manga, state: ReaderUiState) {
		// no-op
	}
}

