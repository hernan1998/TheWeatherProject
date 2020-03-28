package com.example.theweatherproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CityModel (val name: String) : Parcelable {
   public var isLoaded : Boolean = true
}