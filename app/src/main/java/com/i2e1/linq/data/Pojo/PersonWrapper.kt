package com.i2e1.linq.data.Pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tab_persons")
data class PersonWrapper (

        @PrimaryKey(autoGenerate = true)
        var id: Int ,
        @SerializedName("fullName") var fullName: String,
        @SerializedName("email") var email: String,
        @SerializedName("dob") var dob: String,
        @SerializedName("phoneNumber") var phoneNumber: String,
        @SerializedName("pictureUrl") var pictureUrl: String,
        @SerializedName("pictureImageData") var pictureImageData: String?
)
