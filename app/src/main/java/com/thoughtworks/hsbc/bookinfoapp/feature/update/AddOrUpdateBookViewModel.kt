package com.thoughtworks.hsbc.bookinfoapp.feature.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.hsbc.bookinfoapp.service.BookService
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import com.thoughtworks.hsbc.bookinfoapp.utils.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrUpdateBookViewModel @Inject constructor(
    private val bookService: BookService
) : ViewModel() {
    sealed class Action {
        data object UpdateBookSuccess : Action()
        data object AddBookSuccess : Action()
        data object NetError : Action()
    }

    val actionLiveData = ActionLiveData<Action>()

    fun addBook(book: Book) = viewModelScope.launch {
        try {
            bookService.addBook(book)
            actionLiveData.postValue(Action.AddBookSuccess)
        } catch (_: Exception) {
            actionLiveData.postValue(Action.NetError)
        }
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        try {
            bookService.updateBook(book)
            actionLiveData.postValue(Action.UpdateBookSuccess)
        } catch (_: Exception) {
            actionLiveData.postValue(Action.NetError)
        }
    }

}