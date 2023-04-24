package com.example.appnote.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
