package com.thoughtworks.hsbc.bookinfoapp.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.hsbc.bookinfoapp.service.BookService
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class BookDetailViewModelTest {

    @MockK
    lateinit var bookService: BookService

    private val bookIsbn: String = "978-3-16-148410-0"

    private lateinit var actionStates: MutableList<BookDetailViewModel.Action>

    private lateinit var viewModel: BookDetailViewModel

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        actionStates = mutableListOf()
        viewModel = BookDetailViewModel(bookService)
        viewModel.isbn = bookIsbn
        viewModel.actionLiveData.observeForever {
            actionStates.add(it)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_success_when_load_book() = runTest {
        val book = Book(
            bookIsbn,
            "Test Driven Development",
            "Kent Beck",
            "this book is about TDD",
            LocalDate.of(2002, 10, 21)
        )

        coEvery { bookService.getBookByIsbn(bookIsbn) }.returns(book)

        viewModel.loadBook()

        coVerify(exactly = 1) {
            bookService.getBookByIsbn(bookIsbn)
            assertEquals(book, viewModel.bookRepresentation.value?.originalBook)
        }
    }

    @Test
    fun should_success_when_delete_book() = runTest {

        coEvery { bookService.deleteBook(bookIsbn) }.returns(Unit)

        viewModel.deleteBook()

        coVerify(exactly = 1) {
            bookService.deleteBook(bookIsbn)
            assertEquals(BookDetailViewModel.Action.DeleteBookSuccess, actionStates[0])
        }
    }

    @Test
    fun should_error_when_delete_book() = runTest {

        coEvery { bookService.deleteBook(bookIsbn) }.throws(IOException())

        viewModel.deleteBook()

        coVerify(exactly = 1) {
            bookService.deleteBook(bookIsbn)
            assertEquals(BookDetailViewModel.Action.NetError, actionStates[0])
        }
    }
}