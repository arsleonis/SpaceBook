package com.zrz.android.spacebook.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed class SBMessage(
    open val id: String,
    open val time: String,
    open val author: String,
    open val text: String
)

@Parcelize
data class SystemSBMessage(
    override val id: String,
    override val time: String,
    override val author: String = "",
    override val text: String
) : SBMessage(id, time, author, text), Parcelable

@Parcelize
data class OwnSBMessage(
    override val id: String,
    override val time: String,
    override val author: String,
    override val text: String
) : SBMessage(id, time, author, text), Parcelable

@Parcelize
data class MemberSBMessage(
    override val id: String,
    override val time: String,
    override val author: String,
    override val text: String
) : SBMessage(id, time, author, text), Parcelable


