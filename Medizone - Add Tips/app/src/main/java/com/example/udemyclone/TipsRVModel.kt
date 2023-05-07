package com.example.udemyclone

import android.os.Parcel
import android.os.Parcelable

class TipsRVModel() : Parcelable{
    var tipsName: String? = null
    var tipsSuitedFor: String? = null
    var tipsImageLink: String? = null
    var tipsLink: String? = null
    var tipsDescription: String? = null

    constructor(parcel: Parcel) : this() {
        tipsName = parcel.readString()
        tipsSuitedFor = parcel.readString()
        tipsImageLink = parcel.readString()
        tipsLink = parcel.readString()
        tipsDescription = parcel.readString()
    }

    constructor(tipsName: String,  tipsSuitedFor: String, tipsImageLink: String, tipsLink: String, tipsDescription: String) : this() {
        this.tipsName = tipsName
        this.tipsSuitedFor = tipsSuitedFor
        this.tipsImageLink = tipsImageLink
        this.tipsLink = tipsLink
        this.tipsDescription = tipsDescription
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tipsName)
        parcel.writeString(tipsSuitedFor)
        parcel.writeString(tipsImageLink)
        parcel.writeString(tipsLink)
        parcel.writeString(tipsDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TipsRVModel> {
        override fun createFromParcel(parcel: Parcel): TipsRVModel {
            return TipsRVModel(parcel)
        }

        override fun newArray(size: Int): Array<TipsRVModel?> {
            return arrayOfNulls(size)
        }
    }
}