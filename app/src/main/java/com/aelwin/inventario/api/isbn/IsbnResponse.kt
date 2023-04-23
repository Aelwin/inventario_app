package com.aelwin.inventario.api.isbn

import com.google.gson.annotations.SerializedName

data class IsbnResponse(@SerializedName("totalItems") val totalItems: Int,
                        @SerializedName("items") val items: List<Item>) { }

data class Item(@SerializedName("volumeInfo") val volumeInfo: VolumeInfo) { }

data class VolumeInfo(@SerializedName("imageLinks") val imageLinks: ImageLink)

data class ImageLink(@SerializedName("smallThumbnail") val urlImage: String)