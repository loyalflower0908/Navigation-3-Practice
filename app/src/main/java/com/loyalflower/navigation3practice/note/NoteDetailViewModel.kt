package com.loyalflower.navigation3practice.note

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import com.loyalflower.navigation3practice.navigation.NoteListScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteDetailViewModel(
    private val noteId : Int
): ViewModel() {
    private val _noteState = MutableStateFlow(
        sampleNotes.first { it.id == noteId }
    )
    val noteState = _noteState.asStateFlow()
}
