package com.i2e1.linq.data.Pojo

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName


data class PersonWrapper (
        @SerializedName("fullName") var fullName: String,
        @SerializedName("email") var email: String,
        @SerializedName("dob") var dob: String,
        @SerializedName("phoneNumber") var phoneNumber: String,
        @SerializedName("pictureUrl") var pictureUrl: String,
        @SerializedName("pictureBitmap") var pictureBitmap: Bitmap?
)
