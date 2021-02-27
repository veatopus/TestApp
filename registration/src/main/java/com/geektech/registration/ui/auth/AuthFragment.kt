package com.geektech.registration.ui.auth

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.geektech.core.BaseFragment
import com.geektech.registration.R
import com.geektech.registration.databinding.FragmentAuthBinding
import org.koin.java.KoinJavaComponent.inject

class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(R.layout.fragment_auth) {

    private var phone = ""

    override fun getViewModel(): AuthViewModel = inject(AuthViewModel::class.java).value

    override fun getViewBinding(): FragmentAuthBinding? = rootView?.let {
        FragmentAuthBinding.bind(
            it
        )
    }

    override fun initArgs(arg: Bundle) {
        phone = arg.getString(PHONE_KEY, "")
    }

    override fun setUpView() {
        binding?.run {
            textViewRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_enterPhoneFragment)
            }

        }
    }

    companion object {
        const val PHONE_KEY = "phone_key"
    }

}