package com.damask.chucknorrisjokes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Joke (val joke: String) : Parcelable {

    constructor() : this("")

}