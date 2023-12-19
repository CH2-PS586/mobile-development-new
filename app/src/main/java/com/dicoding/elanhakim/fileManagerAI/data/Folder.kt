package com.dicoding.elanhakim.fileManagerAI.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Folder(
    val name: String,
    val photo: Int
) : Parcelable
