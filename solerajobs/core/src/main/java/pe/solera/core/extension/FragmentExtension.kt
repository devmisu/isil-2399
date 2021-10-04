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

fun FragmentManager?.addFragmentToNavigation(
    fragment: Fragment,
    tag: String,
    containerId: Int,
    currentFragment: Fragment? = null
) {
    try {
        this?.let {
            if (!it.fragmentIsAdded(fragment)) {
                it.beginTransaction().let { transaction ->
                    transaction.add(containerId, fragment, tag)
                    currentFragment?.let { cFragment -> transaction.hide(cFragment) }
                    transaction.addToBackStack(tag)
                    transaction.commit()
                }
            } else showExistingFragment(fragment, currentFragment)
        } ?: throw BaseException.FragmentException()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun FragmentManager?.moveBackToFirstFragment(currentFragment: Fragment?, firstFragment: Fragment? = null): Fragment? {
    try {
        this?.let {
            currentFragment?.let { cFragment ->
                if (it.fragments.size > 1 && (firstFragment ?: it.fragments.first()) != cFragment) {
                    it.beginTransaction().let { transaction ->
                        transaction.show(firstFragment ?: it.fragments.first())
                        transaction.hide(cFragment)
                        transaction.commit()
                    }
                    return firstFragment ?: it.fragments.first()
                }
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}

fun FragmentManager?.fragmentIsAdded(fragment: Fragment): Boolean {
    return this?.let {
        return !it.fragments.isNullOrEmpty() && it.fragments.contains(fragment)
    } ?: false
}

fun FragmentManager?.showExistingFragment(fragment: Fragment, currentFragment: Fragment? = null) {
    try {
        this?.let {
            it.beginTransaction().let { transaction ->
                transaction.show(fragment)
                currentFragment?.let { transaction.hide(currentFragment) }
                transaction.commit()
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}