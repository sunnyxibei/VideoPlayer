package cn.com.timeriver.videoplayer.model.bean

import android.os.Parcel
import android.os.Parcelable

data class MusicBean(val data: String, val title: String, val artist: String, val size: Long):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeLong(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicBean> {
        override fun createFromParcel(parcel: Parcel): MusicBean {
            return MusicBean(parcel)
        }

        override fun newArray(size: Int): Array<MusicBean?> {
            return arrayOfNulls(size)
        }
    }
}