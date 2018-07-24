package cn.com.timeriver.videoplayer.model.bean

import android.os.Parcel
import android.os.Parcelable

data class MvAreaBean(var name: String, var code: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MvAreaBean> {
        override fun createFromParcel(parcel: Parcel): MvAreaBean {
            return MvAreaBean(parcel)
        }

        override fun newArray(size: Int): Array<MvAreaBean?> {
            return arrayOfNulls(size)
        }
    }
}
