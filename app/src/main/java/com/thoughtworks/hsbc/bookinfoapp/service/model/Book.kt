package com.thoughtworks.hsbc.bookinfoapp.service.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Book(
    val isbn: String,
    val title: String?,
    val author: String?,
    val description: String?,
    val publishDate: LocalDate?
) : Parcelable
