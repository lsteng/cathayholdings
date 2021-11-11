package reson.cathayholdings.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ZooModel(
    val result: ZooResult
)

data class ZooResult(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val sort: String,
    val results: List<ZooSubResult>
)

@Parcelize
data class ZooSubResult(
    val E_Pic_URL: String,
    val E_Geo: String,
    val E_Info: String,
    val E_no: String,
    val E_Category: String,
    val E_Name: String,
    val E_Memo: String,
    val _id: Int,
    val E_URL: String
): Parcelable