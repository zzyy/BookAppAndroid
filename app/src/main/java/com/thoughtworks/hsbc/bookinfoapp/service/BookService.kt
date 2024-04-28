package com.thoughtworks.hsbc.bookinfoapp.service

import com.thoughtworks.hsbc.bookinfoapp.service.model.Book


interface BookService {
    suspend fun getAllBooks(): List<Book>?
    suspend fun getBookByIsbn(isbn: String): Book?
    suspend fun addBook(book: Book): Book?
    suspend fun updateBook(book: Book): Book?
    suspend fun deleteBook(isbn: String)
}
