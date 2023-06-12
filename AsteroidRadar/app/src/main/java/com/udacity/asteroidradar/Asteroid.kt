package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import com.udacity.asteroidradar.databse.AsteroidDB
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
                @Json(name = "id")    val id: Long,
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable



fun List<Asteroid>.asDatabaseModel():List<AsteroidDB>{
    return map{
        AsteroidDB(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }
}