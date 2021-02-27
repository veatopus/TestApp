package com.geektech.registration.ui.enter_code

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.geektech.core.BaseFragment
import com.geektech.core.hideKeyboard
import com.geektech.core.showToast
import com.geektech.registration.R
import com.geektech.registration.databinding.FragmentEnterCodeBinding
import com.geektech.registration.ui.registration.RegistrationFragment
import org.koin.java.KoinJavaComponent.inject

class EnterCodeFragment: BaseFragment<EnterCodeViewModel, FragmentEnterCodeBinding>(R.layout.fragment_enter_code){

    private var firstPhone: String = "996557071204"

    override fun getViewModel(): EnterCodeViewModel = inject(EnterCodeViewModel::class.java).value

    override fun getViewBinding(): FragmentEnterCodeBinding? = rootView?.let {
        FragmentEnterCodeBinding.bind(
            it
        )
    }

    override fun initArgs(arg: Bundle) {
        firstPhone = arg.getString(RegistrationFragment.FIRST_PHONE_KEY, "996700700541")
        Log.e("fdasdf", "initArgs: $firstPhone")
    }

    override fun setUpView() {
        binding?.run {


            textViewOptionalBtn.setOnClickListener { requireContext().showToast("мне жаль") }

            btnProceed.setOnClickListener {
                if (editTextCode.text.isNotEmpty()) {
                    mViewModel?.verifyCode(editTextCode.text.toString().trim())
                }
            }

            imageButtonClose.setOnClickListener{findNavController().navigateUp()}

            textViewOptionalBtn.setOnClickListener {
                requireContext().showToast("check your phone")
                findNavController().navigateUp()
            }


        }
    }

    override fun setUpViewModelObs() {
        mViewModel?.currentError?.observe(requireActivity(), Observer {
            binding?.editTextCode?.error = it
        })

        mViewModel?.isSuccessVerifyCode = {
            findNavController().run {
                val args = Bundle()
                args.putString(RegistrationFragment.SMS_CODE_KEY, binding?.editTextCode?.text.toString().trim())
                args.putString(RegistrationFragment.FIRST_PHONE_KEY, firstPhone)

                navigate(R.id.action_enterCodeFragment_to_registrationFragment, args)
                hideKeyboard()
            }
        }
    }
}