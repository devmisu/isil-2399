package pe.solera.solerajobs.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.ScreenDimensions
import pe.solera.core.extension.addFragment
import pe.solera.solerajobs.R
import pe.solera.solerajobs.ui.dialog.chart.ProjectDetailFragment
import pe.solera.solerajobs.ui.dialog.login.LoginDialogFragment
import javax.inject.Inject

enum class ChildBottomSheetType {
    Login, ChartDetail;
}

@AndroidEntryPoint
class HostBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var screenDimensions: ScreenDimensions

    private val args : Bundle? get() = arguments?.getBundle(CHILD_BOTTOM_SHEET_CONTENT)

    private lateinit var type: ChildBottomSheetType

    var actionListener : HostBottomSheetActionListener<Any>? = null

    companion object {
        const val CHILD_BOTTOM_SHEET_CONTENT = "CHILD_BOTTOM_SHEET_CONTENT"
        const val CHILD_BOTTOM_SHEET_TYPE = "CHILD_BOTTOM_SHEET_TYPE"
        const val HOST_BOTTOM_SHEET_BEHAVIOR = "HOST_BOTTOM_SHEET_BEHAVIOR"
        const val HOST_BOTTOM_SHEET_INVALID_BUILDER_MSG = "HOST_BOTTOM_SHEET_INVALID_BUILDER_MSG"
        const val CHILD_BOTTOM_SHEET_ACTION = "CHILD_BOTTOM_SHEET_ACTION"

        interface HostBottomSheetActionListener<in T> : Parcelable {
            fun action(value: T?, closed: Boolean = false)
            override fun describeContents(): Int = 0
            override fun writeToParcel(dest: Parcel?, flags: Int) {
                //Do nothing
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_host, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            bottomSheetDialog.setCancelable(this.isCancelable)
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) ?: return@setOnShowListener
            bottomSheet.setBackgroundColor(Color.TRANSPARENT)
            arguments?.getInt(HOST_BOTTOM_SHEET_BEHAVIOR)?.let { behavior ->
                if (behavior != 0) {
                    val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                    bottomSheetBehavior.isHideable = this.isCancelable
                    if (behavior != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.peekHeight = screenDimensions.height
                        val lp : ViewGroup.LayoutParams? = bottomSheet.layoutParams
                        lp?.let { validLp ->
                            validLp.height = screenDimensions.height
                            bottomSheet.layoutParams = validLp
                        }
                    } else {
                        bottomSheetBehavior.peekHeight = bottomSheet.height
                    }
                    bottomSheetBehavior.state = behavior
                    when(behavior) {
                        BottomSheetBehavior.STATE_EXPANDED -> Log.d("HostBottomSheet - Behavior", "STATE_EXPANDED")
                        BottomSheetBehavior.STATE_HIDDEN -> Log.d("HostBottomSheet - Behavior", "STATE_HIDDEN")
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.d("HostBottomSheet - Behavior", "STATE_HALF_EXPANDED")
                        BottomSheetBehavior.STATE_COLLAPSED -> Log.d("HostBottomSheet - Behavior", "STATE_COLLAPSED")
                    }
                }
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getSerializable(CHILD_BOTTOM_SHEET_TYPE)?.let { validType ->
            this.type = validType as ChildBottomSheetType
            this.actionListener = arguments?.getParcelable(CHILD_BOTTOM_SHEET_ACTION)
            this.setupFragmentContent()
        } ?: kotlin.run {
            println(HOST_BOTTOM_SHEET_INVALID_BUILDER_MSG)
        }
    }

    private fun setupFragmentContent() {
        this.childFragmentManager.addFragment(getFragmentByType(), this.type.name, R.id.frameLtyHostBottomSheetContainer)
    }

    private fun getFragmentByType() : Fragment = when(this.type) {
        ChildBottomSheetType.Login -> LoginDialogFragment.newInstance(args)
        ChildBottomSheetType.ChartDetail -> ProjectDetailFragment.newInstance(args)
    }

    class Builder {

        private var arguments : Bundle = Bundle()
        private var isHideable : Boolean = true

        fun setType(type: ChildBottomSheetType) = apply {
            this.arguments.putSerializable(CHILD_BOTTOM_SHEET_TYPE, type)
        }

        fun setHideable(isHideable: Boolean) = apply {
            this.isHideable = isHideable
        }

        fun setHostBottomSheetBehavior(behavior: Int) = apply {
            this.arguments.putInt(HOST_BOTTOM_SHEET_BEHAVIOR, behavior)
        }

        fun setFragmentContent(bundle: Bundle) = apply {
            this.arguments.putBundle(CHILD_BOTTOM_SHEET_CONTENT, bundle)
        }

        fun buildAndShow(fragmentManager: FragmentManager) : HostBottomSheet {
            val hostBottomSheet = HostBottomSheet()
            hostBottomSheet.arguments = this.arguments
            hostBottomSheet.isCancelable = isHideable
            hostBottomSheet.show(fragmentManager, HostBottomSheet::class.java.name)
            return hostBottomSheet
        }

        //Secondary

        fun <T> setActionListener(action: (value: T) -> Unit, negative: (() -> Unit)? = null) = apply {
            this.arguments.putParcelable(CHILD_BOTTOM_SHEET_ACTION, object : HostBottomSheetActionListener<T> {
                override fun action(value: T?, closed: Boolean) {
                    value?.let { action.invoke(it) }
                    if (closed) { negative?.invoke() }
                }
            })
        }
    }
}