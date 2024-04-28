package com.thoughtworks.hsbc.bookinfoapp.service

import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import com.thoughtworks.hsbc.bookinfoapp.service.remote.BookRemoteApi
import com.thoughtworks.hsbc.bookinfoapp.service.remote.HttpClients
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class BookServiceImpl @Inject constructor(
    private val remoteApi: BookRemoteApi
) : BookService {
    override suspend fun getAllBooks(): List<Book>? {
        return remoteApi.getAllBooks()
    }

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return remoteApi.getBookByIsbn(isbn)
    }

    override suspend fun addBook(book: Book): Book? {
        return remoteApi.addBook(book)
    }

    override suspend fun updateBook(book: Book): Book? {
        return remoteApi.updateBook(book.isbn, book)
    }

    override suspend fun deleteBook(isbn: String) {
        return remoteApi.deleteBook(isbn)
    }

}