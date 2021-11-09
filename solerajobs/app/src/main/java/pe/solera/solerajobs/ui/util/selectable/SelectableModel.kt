package pe.solera.solerajobs.ui.util.selectable

import android.os.Parcel
import android.os.Parcelable
import java.lang.reflect.ParameterizedType

class SelectableModel<T: Parcelable>(
    var model: T? = null,
    var identifier: String? = "",
    var isSelected: Boolean = false,
) : Parcelable {

    val EMPTY : String = "empty"

    constructor(parcel: Parcel) : this(
        model = parcel.readParcelable(Class.forName(parcel.readString()).classLoader),
        identifier = parcel.readString(),
        isSelected = parcel.readByte() != 0.toByte()
    ) {
    }

    fun classLoader() : ClassLoader {
        val type = javaClass.genericSuperclass
        val params = (type as ParameterizedType).actualTypeArguments
        return (params[0] as Class<T>).classLoader
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(identifier)
        parcel.writeByte(if (isSelected) 1 else 0)
        parcel.writeString(if(model==null) EMPTY else model!!::class.java.name)
        parcel.writeParcelable(model, flags)
    }

    override fun describeContents(): Int = 0

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<SelectableModel<Parcelable>> {
            override fun createFromParcel(source: Parcel): SelectableModel<Parcelable> {
                return SelectableModel<Parcelable>(source)
            }

            override fun newArray(size: Int): Array<SelectableModel<Parcelable>?> {
                return arrayOfNulls(size)
            }
        }
    }

}
