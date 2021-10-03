package pe.solera.solerajobs.ui.dialog.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.BottomSheetLoginBinding
import pe.solera.solerajobs.ui.dialog.ChildBottomSheet

class LoginDialogFragment : ChildBottomSheet() {

    private lateinit var binding : BottomSheetLoginBinding

    companion object {
        fun newInstance(bundle: Bundle? = null) : Fragment {
            return LoginDialogFragment().apply {
                bundle?.let { arguments = it }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            doPositiveAction(true)
        }
    }

}