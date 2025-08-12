package com.loyalflower.navigation3practice.di

import com.loyalflower.navigation3practice.note.NoteDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::NoteDetailViewModel)
}