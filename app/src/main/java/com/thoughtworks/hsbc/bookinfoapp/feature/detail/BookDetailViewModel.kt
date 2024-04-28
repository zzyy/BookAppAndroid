package com.thoughtworks.hsbc.bookinfoapp.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.hsbc.bookinfoapp.feature.model.BookRepresentation
import com.thoughtworks.hsbc.bookinfoapp.service.BookService
import com.thoughtworks.hsbc.bookinfoapp.utils.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookService: BookService
) : ViewModel() {
    sealed class Action {
        data object DeleteBookSuccess : Action()
        data object NetError : Action()
    }

    var isbn: String? = null

    val actionLiveData = ActionLiveData<Action>()

    private val _bookRepresentation = MutableLiveData<BookRepresentation>()
    val bookRepresentation: LiveData<BookRepresentation> = _bookRepresentation

    fun loadBook() = viewModelScope.launch {
        val isbn = isbn.orEmpty()
        val book = bookService.getBookByIsbn(isbn)
        _bookRepresentation.postValue(BookRepresentation(book))
    }

    fun deleteBook() = viewModelScope.launch {
        try {
            val isbn = isbn.orEmpty()
            bookService.deleteBook(isbn)
            actionLiveData.postValue(Action.DeleteBookSuccess)
        } catch (_: Exception) {
            actionLiveData.postValue(Action.NetError)
        }
    }
}