package com.android.zmtestkotlinwithmvvm.models.productList

data class Pagination(
    val page: Int,
    val rowsPerPage: Int,
    val totalCount: Int,
    val totalPage: Int
)