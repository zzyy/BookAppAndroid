package com.thoughtworks.hsbc.bookinfoapp.feature.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.thoughtworks.hsbc.bookinfoapp.databinding.ActivityBookDetailBinding
import com.thoughtworks.hsbc.bookinfoapp.feature.update.AddOrUpdateBookActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_EXTRA_BOOK_ISBN = "extra_book_isbn"

        fun createIntent(context: Context, isbn: String): Intent {
            return Intent(context, BookDetailActivity::class.java).apply {
                putExtra(INTENT_EXTRA_BOOK_ISBN, isbn)
            }
        }
    }

    private val isbn by lazy { intent.getStringExtra(INTENT_EXTRA_BOOK_ISBN) }

    private lateinit var binding: ActivityBookDetailBinding

    private val viewModel by viewModels<BookDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.activity = this
        binding.lifecycleOwner = this
        viewModel.isbn = isbn
        viewModel.loadBook()

        observerActions()
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadBook()
    }

    private fun observerActions() {
        viewModel.actionLiveData.observe(
            this
        ) {
            when (it) {
                is BookDetailViewModel.Action.DeleteBookSuccess -> finish()

                is BookDetailViewModel.Action.NetError ->
                    Toast.makeText(this, "network error, try it later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClickUpdateBookButton() {
        viewModel.bookRepresentation.value?.originalBook?.let {
            val intent = AddOrUpdateBookActivity.updateBookIntent(this, it)
            startActivity(intent)
        }
    }
}