package com.example.dynamicviewpagertestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicviewpagertestapp.dataSource.ApiService
import com.example.dynamicviewpagertestapp.domain.PageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val dataSource: ApiService): ViewModel() {

    private val _notesStateFlow: MutableStateFlow<List<Int>?> = MutableStateFlow(null)
    val notesStateFlow = _notesStateFlow.asStateFlow()

    private val _pageStateFlow: MutableStateFlow<PageResponse?> = MutableStateFlow(null)
    val pageStateFlow = _pageStateFlow.asStateFlow()

    fun getAllNotes() {
        viewModelScope.launch {
            dataSource.getNotes().apply {
                if(this.isSuccessful) {
                    _notesStateFlow.emit(this.body())
                }
            }
        }
    }

    fun getPage(id: Int) {
        viewModelScope.launch {
            dataSource.getNoteById(id).apply {
                if(this.isSuccessful) {
                    _pageStateFlow.emit(this.body())
                }
            }
        }
    }

}