package com.thoughtworks.hsbc.bookinfoapp.service.remote

import com.thoughtworks.hsbc.bookinfoapp.service.model.Book
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface BookRemoteApi {

    @GET("/api/books")
    suspend fun getAllBooks(): List<Book>?

    @POST("/api/books")
    suspend fun addBook(@Body book: Book): Book?

    @GET("/api/books/{isbn}")
    suspend fun getBookByIsbn(@Path("isbn") isbn: String): Book?

    @PUT("/api/books/{isbn}")
    suspend fun updateBook(@Path("isbn") isbn: String, @Body book: Book?): Book?

    @DELETE("/api/books/{isbn}")
    suspend fun deleteBook(@Path("isbn") isbn: String)
}