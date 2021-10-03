package pe.solera.solerajobs.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class ChildBottomSheet : Fragment() {

    private var actionListener : HostBottomSheet.Companion.HostBottomSheetActionListener<Any>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActionListener()
    }

    private fun getActionListener() {
        parentFragment?.let {
            if (it is HostBottomSheet) {
                this.actionListener = it.actionListener
            }
        }
    }

    private fun hideHostBottomSheet() {
        parentFragment?.let {
            if (it is HostBottomSheet) {
                it.dismiss()
            }
        }
    }

    internal fun doPositiveAction(value: Any) {
        this.actionListener?.action(value)
        hideHostBottomSheet()
    }

    internal fun closeAction() {
        this.actionListener?.action(null, true)
        hideHostBottomSheet()
    }
}