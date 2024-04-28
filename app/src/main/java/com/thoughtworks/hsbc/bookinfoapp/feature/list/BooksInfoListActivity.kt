package com.thoughtworks.hsbc.bookinfoapp.feature.list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.hsbc.bookinfoapp.BR
import com.thoughtworks.hsbc.bookinfoapp.R
import com.thoughtworks.hsbc.bookinfoapp.databinding.ActivityBookInfoListBinding
import com.thoughtworks.hsbc.bookinfoapp.feature.update.AddOrUpdateBookActivity
import com.thoughtworks.hsbc.bookinfoapp.feature.detail.BookDetailActivity
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import dagger.hilt.android.AndroidEntryPoint
import me.tatarka.bindingcollectionadapter2.ItemBinding

@AndroidEntryPoint
class BooksInfoListActivity : AppCompatActivity() {

    private val viewModel by viewModels<BookInfoListViewModel>()
    private lateinit var binding: ActivityBookInfoListBinding

    val itemBinding = ItemBinding.of<Book>(BR.book, R.layout.item_book_list)
        .bindExtra(BR.clickListener, this::onClickBookItem)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookInfoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.activity = this
        binding.lifecycleOwner = this
        viewModel.actionLiveData.observe(this) {
            when (it) {
                is BookInfoListViewModel.Action.NetError ->
                    Toast.makeText(this, "network error, try it later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchBooks()
    }

    private fun onClickBookItem(book: Book) {
        val intent = BookDetailActivity.createIntent(this, book.isbn)
        startActivity(intent)
    }

    fun onClickAddBook() {
        val intent = AddOrUpdateBookActivity.createBookIntent(this)
        startActivity(intent)
    }
}