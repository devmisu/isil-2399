package pe.solera.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pe.solera.core.BaseException

fun FragmentManager?.addFragment(fragment: Fragment, tag: String, containerId: Int) {
    try {
        this?.let {
            it.beginTransaction().let { transaction ->
                transaction.add(containerId, fragment, tag)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } ?: throw BaseException.FragmentException()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}