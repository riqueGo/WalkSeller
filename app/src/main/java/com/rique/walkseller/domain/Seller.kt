package com.rique.walkseller.domain

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seller(
    val id: String,
    val position: LatLng,
    val name: String,
    val description: String,
    val phone: String
) : Parcelable
