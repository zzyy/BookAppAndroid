package com.thoughtworks.hsbc.bookinfoapp.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import com.thoughtworks.hsbc.bookinfoapp.service.BookService
import com.thoughtworks.hsbc.bookinfoapp.utils.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList
import javax.inject.Inject

@HiltViewModel
class BookInfoListViewModel @Inject constructor(val bookService: BookService) : ViewModel() {
    sealed class Action {
        data object NetError : Action()
    }

    val actionLiveData = ActionLiveData<Action>()

    private val bookDiffCallback: DiffUtil.ItemCallback<Book> = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.isbn == newItem.isbn
        }
    }

    val bookList: AsyncDiffObservableList<Book> = AsyncDiffObservableList(bookDiffCallback)

    fun fetchBooks() = viewModelScope.launch {
        try {
            val books = bookService.getAllBooks()
            bookList.update(books)
        } catch (_: Exception) {
            actionLiveData.postValue(Action.NetError)
        }
    }
}