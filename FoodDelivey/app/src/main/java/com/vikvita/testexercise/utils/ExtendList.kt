package com.vikvita.testexercise.utils

import com.vikvita.testexercise.data.dto.DishesItem

fun List<DishesItem>.doubleIt():List<DishesItem>{
    val doubledList= mutableListOf<DishesItem>()
    doubledList.addAll(this)
    doubledList.addAll(this)

    return doubledList.toList()
}