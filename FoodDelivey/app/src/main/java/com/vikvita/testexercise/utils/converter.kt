package com.vikvita.testexercise.utils

import com.vikvita.testexercise.R
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem

fun DishesItem.converToBasket():BasketItem=BasketItem(
    pictureUrl=this.pictureUrl,
    id=this.id,
    text=this.name,
    price=this.price,
    weight = this.weight
)