package com.thoughtworks.hsbc.bookinfoapp.feature.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.thoughtworks.hsbc.bookinfoapp.databinding.ActivityAddOrUpdateBookBinding
import com.thoughtworks.hsbc.bookinfoapp.feature.model.BookRepresentation
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddOrUpdateBookActivity : AppCompatActivity() {
    companion object {
        private const val INTENT_EXTRA_DATA_BOOK = "extra_book"

        fun createBookIntent(context: Context): Intent {
            return Intent(context, AddOrUpdateBookActivity::class.java)
        }

        fun updateBookIntent(context: Context, book: Book): Intent {
            return Intent(context, AddOrUpdateBookActivity::class.java).apply {
                putExtra(INTENT_EXTRA_DATA_BOOK, book)
            }
        }
    }

    private lateinit var binding: ActivityAddOrUpdateBookBinding

    private val viewModel by viewModels<AddOrUpdateBookViewModel>()

    private val originalBook by lazy { intent.getParcelableExtra<Book>(INTENT_EXTRA_DATA_BOOK) }

    val isUpdateBook: Boolean by lazy { originalBook != null }

    val bookRepresentation by lazy { BookRepresentation(originalBook) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.toolbar.title = if (isUpdateBook) "Update book" else "Add Book"
        observerActions()
        binding.publishDate.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener { showDataPicker() }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    showDataPicker()
                }
            }
        }
    }

    private fun observerActions() {
        viewModel.actionLiveData.observe(
            this
        ) {
            when (it) {
                is AddOrUpdateBookViewModel.Action.UpdateBookSuccess,
                AddOrUpdateBookViewModel.Action.AddBookSuccess ->
                    finish()

                is AddOrUpdateBookViewModel.Action.NetError ->
                    Toast.makeText(this, "network error, try it later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .build()
        .apply {
            addOnPositiveButtonClickListener {
                val date = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                bookRepresentation.publishDate.set(date.format(DateTimeFormatter.ISO_DATE))
            }
        }

    private fun showDataPicker() {
        datePicker.show(this.supportFragmentManager, "datePicker")
    }

    fun onClickConfirm() {
        val book = bookRepresentation.getBook()
        if (isUpdateBook) {
            viewModel.updateBook(book)
        } else {
            viewModel.addBook(book)
        }
    }
}