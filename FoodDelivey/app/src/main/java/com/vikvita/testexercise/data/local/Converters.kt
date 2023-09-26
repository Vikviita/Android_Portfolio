package com.vikvita.testexercise.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vikvita.testexercise.utils.TagsOfDish


class Converters {

    @TypeConverter
    fun listToJson(value: List<TagsOfDish>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<TagsOfDish>::class.java).toList()
}
