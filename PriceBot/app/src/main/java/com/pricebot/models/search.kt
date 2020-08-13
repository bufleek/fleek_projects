package com.pricebot.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Search(var title: String): Parcelable

data class SearchResult(var site: String, var title: String, var price: String, var oldPrce: String, var percentDiscount: String, var link: String)

