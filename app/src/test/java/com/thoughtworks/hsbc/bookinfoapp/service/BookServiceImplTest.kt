package com.thoughtworks.hsbc.bookinfoapp.service

import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import com.thoughtworks.hsbc.bookinfoapp.service.remote.BookRemoteApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class BookServiceImplTest {

    private lateinit var bookService: BookServiceImpl

    @MockK
    lateinit var bookRemoteApi: BookRemoteApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bookService = BookServiceImpl(bookRemoteApi)
    }

    @Test
    fun getAllBooks() = runBlocking {
        val book1 = Book(
            "978-3-16-148410-0",
            "Test Driven Development",
            "Kent Beck",
            "this book is about TDD",
            LocalDate.of(2002, 10, 21)
        )

        val book2 = Book(
            "978-3-16-148411-0",
            "Clean Code",
            "Robert C. Martin",
            "book about clean code",
            LocalDate.of(2008, 8, 11)
        )

        coEvery { bookRemoteApi.getAllBooks() } returns listOf(book1, book2)
        val books = bookService.getAllBooks()

        assertEquals(2, books?.size)
        assertEquals(book1, books?.get(0))
        assertEquals(book2, books?.get(1))
    }

    @Test
    fun getBookByIsbn() = runBlocking {
        val isbn = "978-3-16-148410-0"
        val book1 = Book(
            isbn,
            "Test Driven Development",
            "Kent Beck",
            "this book is about TDD",
            LocalDate.of(2002, 10, 21)
        )
        coEvery { bookRemoteApi.getBookByIsbn(isbn) }.returns(book1)

        val resultBook = bookService.getBookByIsbn(isbn)

        assertEquals(isbn, resultBook?.isbn)
        assertEquals(book1.title, resultBook?.title)
    }

    @Test
    fun addBook() = runBlocking {
        val book = Book(
            "978-3-16-148410-0",
            "Test Driven Development",
            "Kent Beck",
            "this book is about TDD",
            LocalDate.of(2002, 10, 21)
        )

        coEvery { bookRemoteApi.addBook(book) }.returns(book)

        bookService.addBook(book)

        coVerify {
            bookRemoteApi.addBook(book)
        }
    }

    @Test
    fun updateBook() = runBlocking {
        val book = Book(
            "978-3-16-148410-0",
            "Test Driven Development",
            "Kent Beck",
            "this book is about TDD",
            LocalDate.of(2002, 10, 21)
        )

        coEvery { bookRemoteApi.updateBook(book.isbn, book) }.returns(book)

        bookService.updateBook(book)

        coVerify {
            bookRemoteApi.updateBook(book.isbn, book)
        }
    }

    @Test
    fun deleteBook() = runBlocking {
        val isbn = "978-3-16-148410-0"

        coEvery { bookRemoteApi.deleteBook(isbn) }.returns(Unit)

        bookService.deleteBook(isbn)

        coVerify {
            bookRemoteApi.deleteBook(isbn)
        }
    }
}