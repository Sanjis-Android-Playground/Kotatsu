package org.koitharu.kotatsu.settings.about.changelog

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.jsoup.internal.StringUtil

import org.koitharu.kotatsu.core.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChangelogViewModel @Inject constructor() : BaseViewModel() {

	val changelog = MutableStateFlow<String?>(null)

	init {
		// Changelog disabled
	}
}

