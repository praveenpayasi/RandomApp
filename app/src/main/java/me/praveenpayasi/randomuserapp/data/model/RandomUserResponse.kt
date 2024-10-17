package me.praveenpayasi.randomuserapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomUserResponse(
    val info: Info,
    val results: List<Result>
) : Parcelable