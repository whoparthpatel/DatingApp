package com.example.datingapp.model

import android.os.Parcel
import android.os.Parcelable

data class UsersDataClass(
    val userId : String? = null,
    val fname : String? = null,
    val lname : String? = null,
    val emali : String? = null,
    val password : String? = null,
    val repassword : String? = null,
    val name : String? = null,
    val dob : String? = null,
    val phone : String? = null,
    val gender : String? = null,
    val age : String? = null,
    val bio : String? = null,
    val imgUrl : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(fname)
        parcel.writeString(lname)
        parcel.writeString(emali)
        parcel.writeString(password)
        parcel.writeString(repassword)
        parcel.writeString(name)
        parcel.writeString(dob)
        parcel.writeString(phone)
        parcel.writeString(gender)
        parcel.writeString(age)
        parcel.writeString(bio)
        parcel.writeString(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersDataClass> {
        override fun createFromParcel(parcel: Parcel): UsersDataClass {
            return UsersDataClass(parcel)
        }

        override fun newArray(size: Int): Array<UsersDataClass?> {
            return arrayOfNulls(size)
        }
    }
}
