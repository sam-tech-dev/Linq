package com.i2e1.linq.data.Pojo
import com.google.gson.annotations.SerializedName



data class PersonsApiResponse(
		@SerializedName("results") var results: List<Result>,
		@SerializedName("info") var info: Info
)

data class Result(
		@SerializedName("name") var name: Name,
		@SerializedName("email") var email: String,
		@SerializedName("dob") var dob: Dob,
		@SerializedName("phone") var phone: String,
		@SerializedName("picture") var picture: Picture
)

data class Name(
		@SerializedName("title") var title: String,
		@SerializedName("first") var first: String,
		@SerializedName("last") var last: String
)

data class Dob(
		@SerializedName("date") var date: String,
		@SerializedName("age") var age: Int
)

data class Picture(
		@SerializedName("large") var large: String,
		@SerializedName("medium") var medium: String,
		@SerializedName("thumbnail") var thumbnail: String
)

data class Info(
		@SerializedName("seed") var seed: String,
		@SerializedName("results") var results: Int,
		@SerializedName("page") var page: Int,
		@SerializedName("version") var version: String
)