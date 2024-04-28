package com.thoughtworks.hsbc.bookinfoapp.feature.update

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
class AddOrUpdateBookViewModelTest {

    private val book = Book(
        "978-3-16-148410-0",
        "Test Driven Development",
        "Kent Beck",
        "this book is about TDD",
        LocalDate.of(2002, 10, 21)
    )

    private lateinit var actionStates: MutableList<AddOrUpdateBookViewModel.Action>

    private lateinit var viewModel: AddOrUpdateBookViewModel

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var bookService: BookService

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        actionStates = mutableListOf()
        viewModel = AddOrUpdateBookViewModel(bookService)
        viewModel.actionLiveData.observeForever {
            actionStates.add(it)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_success_when_add_book() = runTest {
        coEvery { bookService.addBook(book) }.returns(book)

        viewModel.addBook(book)

        coVerify(exactly = 1) {
            bookService.addBook(book)
        }
        assertEquals(AddOrUpdateBookViewModel.Action.AddBookSuccess, actionStates[0])
    }

    @Test
    fun should_error_when_add_book() = runTest {
        coEvery { bookService.addBook(book) }.throws(IOException())

        viewModel.addBook(book)

        coVerify(exactly = 1) {
            bookService.addBook(book)
        }
        assertEquals(AddOrUpdateBookViewModel.Action.NetError, actionStates[0])
    }

    @Test
    fun should_success_when_update_book() = runTest {
        coEvery { bookService.updateBook(book) }.returns(book)

        viewModel.updateBook(book)

        coVerify(exactly = 1) {
            bookService.updateBook(book)
        }
        assertEquals(AddOrUpdateBookViewModel.Action.UpdateBookSuccess, actionStates[0])
    }

    @Test
    fun should_error_when_update_book() = runTest {
        coEvery { bookService.updateBook(book) }.throws(IOException())

        viewModel.updateBook(book)

        coVerify(exactly = 1) {
            bookService.updateBook(book)
        }
        assertEquals(AddOrUpdateBookViewModel.Action.NetError, actionStates[0])
    }
}