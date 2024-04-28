package com.thoughtworks.hsbc.bookinfoapp.feature.model

import androidx.databinding.ObservableField
import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class BookRepresentation(
    val originalBook: Book?
) {
    private val dateFormatter = DateTimeFormatter.ISO_DATE

    val isbn = ObservableField<String>(originalBook?.isbn)
    val title = ObservableField<String>(originalBook?.title)
    val author = ObservableField<String>(originalBook?.author)
    val description = ObservableField<String>(originalBook?.description)
    val publishDate = ObservableField<String>(originalBook?.publishDate?.format(dateFormatter))

    fun getBook(): Book {
        val date: LocalDate? = try {
            LocalDate.parse(publishDate.get(), dateFormatter)
        } catch (_: Exception) {
            null
        }

        return Book(
            isbn = isbn.get().orEmpty(),
            title = title.get(),
            author = author.get(),
            description = description.get(),
            publishDate = date
        )
    }
}