package cn.com.timeriver.videoplayer.model.bean

import android.os.Parcel
import android.os.Parcelable

data class PlayerBean(var id: Int, var title: String, var url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerBean> {
        override fun createFromParcel(parcel: Parcel): PlayerBean {
            return PlayerBean(parcel)
        }

        override fun newArray(size: Int): Array<PlayerBean?> {
            return arrayOfNulls(size)
        }
    }
}