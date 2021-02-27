package com.geektech.registration.ui.registration

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import com.geektech.core.BaseFragment
import com.geektech.core.showToast
import com.geektech.registration.R
import com.geektech.registration.databinding.FragmentRegistrationBinding
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class RegistrationFragment :
    BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>(R.layout.fragment_registration) {

    private var secretQuestion: Int? = null
    private var gender: Int? = null
    private var nationals: Int? = null
    private var trafficSource: Int? = null
    private var smsCode = ""
    private var firstPhone = ""
    private var uDate = ""
    private var countryCode = ""

    override fun getViewModel(): RegistrationViewModel =
        inject(RegistrationViewModel::class.java).value

    override fun getViewBinding(): FragmentRegistrationBinding? = rootView?.let {
        FragmentRegistrationBinding.bind(
            it
        )
    }

    override fun initArgs(arg: Bundle) {
        smsCode = arg.getString(SMS_CODE_KEY, "")
        firstPhone = arg.getString(FIRST_PHONE_KEY, "")
        Log.e("fdasdf", "initArgs: $firstPhone")
    }

    override fun setUpView() {
        binding?.run {
            textViewDidntDetCoe.setOnClickListener { requireContext().showToast("мне жаль") }

            tvPhone.text = firstPhone

            tvChoiceDate.setOnClickListener { callDatePicker() }

            spinnerListSecretQuestion.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    secretQuestion =
                        mViewModel?.listSecretQuestion?.value?.get(position)?.id?.toInt()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
            spinnerListGender.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    gender = mViewModel?.listGender?.value?.get(position)?.id?.toInt()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
            spinnerListNationality.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    nationals = mViewModel?.listNationals?.value?.get(position)?.id
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
            spinnerListTrafficSource.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    trafficSource = mViewModel?.listTrafficSource?.value?.get(position)?.id?.toInt()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

            spinnerListCountryCode.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    countryCode = mViewModel?.listAvailableCountry?.value?.get(position)?.phone_code.toString()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

            btnProceed.setOnClickListener {
                if (!checkBox.isChecked) {
                    checkBox.error = "!!"
                    return@setOnClickListener
                }

                val lastName = binding?.etLastName?.text?.toString()?.trim()
                val firstName = binding?.etName?.text?.toString()?.trim()
                val secondName = binding?.etSecondName?.text?.toString()?.trim()
                val secondPhone = countryCode + binding?.etDopPhone?.text?.toString()?.trim()
                val response = binding?.etAskQuestion?.text?.toString()?.trim()

                if (lastName != null && lastName.isNotEmpty() &&
                    firstName != null && firstName.isNotEmpty() && uDate.isNotEmpty() &&
                    secondName != null && secondName.isNotEmpty() &&
                    firstPhone.isNotEmpty() && secondPhone.isNotEmpty() &&
                    response != null && response.isNotEmpty() &&
                    gender != null && nationals != null
                    && secretQuestion != null && trafficSource != null && smsCode.isNotEmpty()
                ) {
                    mViewModel?.register(
                        lastName,
                        firstName,
                        secondName,
                        uDate,
                        gender!!,
                        nationals!!,
                        firstPhone,
                        secondPhone,
                        secretQuestion!!,
                        response,
                        trafficSource!!,
                        smsCode
                    )
                }
            }
        }
    }

    override fun setUpViewModelObs() {
        mViewModel?.run {
            listSecretQuestion.observe(requireActivity(), Observer {
                val names = mutableListOf<String>()
                for (i in it) i.name?.let { name -> names.add(name) }
                binding?.spinnerListSecretQuestion?.let { spinner -> listToSpinner(names, spinner) }
            })
            listGender.observe(requireActivity(), Observer {
                val names = mutableListOf<String>()
                for (i in it) i.name?.let { name -> names.add(name) }
                binding?.spinnerListGender?.let { spinner -> listToSpinner(names, spinner) }
            })
            listNationals.observe(requireActivity(), Observer {
                val names = mutableListOf<String>()
                for (i in it) i.name?.let { name -> names.add(name) }
                binding?.spinnerListNationality?.let { spinner -> listToSpinner(names, spinner) }
            })
            listTrafficSource.observe(requireActivity(), Observer {
                val names = mutableListOf<String>()
                for (i in it) i.name?.let { name -> names.add(name) }
                binding?.spinnerListTrafficSource?.let { spinner -> listToSpinner(names, spinner) }
            })
            listAvailableCountry.observe(requireActivity(), Observer {
                val names = mutableListOf<String>()
                for (i in it) i.phone_code?.let { name -> names.add(name) }
                binding?.spinnerListCountryCode?.let { spinner -> listToSpinner(names, spinner) }
            })
        }
    }

    private fun listToSpinner(list: MutableList<String>, spinner: Spinner) {
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), R.layout.spinner_item,
            list
        )

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerArrayAdapter
    }

    private fun callDatePicker() {
        // получаем текущую дату
        val cal: Calendar = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        // инициализируем диалог выбора даты текущими значениями
        val datePickerDialog = DatePickerDialog(
            requireActivity(), { _, year, monthOfYear, dayOfMonth ->
                uDate = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                binding?.tvChoiceDate?.text = uDate
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    companion object {
        const val SMS_CODE_KEY = "SMS_CODE_KEY"
        const val FIRST_PHONE_KEY = "FIRST_PHONE_KEY"
    }
}
