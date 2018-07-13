package com.past3.ketro.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenericVehicleContainer(val key: String, val valueItem: String) : Parcelable