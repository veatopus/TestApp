package com.geektech.registration.ui.registration

import androidx.lifecycle.MutableLiveData
import com.geektech.core.BaseViewModel
import kg.bishkek.ruslan.data.models.RepositoryApi
import kg.bishkek.ruslan.data.models.Status
import kg.bishkek.ruslan.data.models.list_available_country.Country
import kg.bishkek.ruslan.data.models.list_gender.Gender
import kg.bishkek.ruslan.data.models.list_nationality.National
import kg.bishkek.ruslan.data.models.list_secret_question.Question
import kg.bishkek.ruslan.data.models.list_traffic_source.Traffic

class RegistrationViewModel(private val api: RepositoryApi) : BaseViewModel() {

    val listGender = MutableLiveData<MutableList<Gender>>()
    val listNationals = MutableLiveData<MutableList<National>>()
    val listAvailableCountry = MutableLiveData<MutableList<Country>>()
    val listTrafficSource = MutableLiveData<MutableList<Traffic>>()
    val listSecretQuestion = MutableLiveData<MutableList<Question>>()

    init {
        api.getListGender().observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                resource.data?.result?.let { listGender.value = it.toMutableList() }
        }

        api.getListNationality().observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                resource.data?.result?.let { listNationals.value = it.toMutableList() }
        }

        api.getListAvailableCountry().observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                resource.data?.result?.let { listAvailableCountry.value = it.toMutableList() }
        }

        api.getListTrafficSource().observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                resource.data?.result?.let { listTrafficSource.value = it.toMutableList() }
        }

        api.getListSecretQuestions().observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                resource.data?.result?.let { listSecretQuestion.value = it.toMutableList() }
        }
    }

    fun register(
        lastName: String,
        firstName: String,
        secondName: String,
        uDate: String,
        gender: Int,
        nationality: Int,
        firstPhone: String,
        secondPhone: String,
        question: Int,
        response: String,
        trafficSource: Int,
        smsCode: String
    ) {
        api.registrations(
            lastName,
            firstName,
            secondName,
            uDate,
            gender,
            nationality,
            firstPhone,
            secondPhone,
            question,
            response,
            trafficSource,
            smsCode
        ).observeForever { resource ->
            if (resource.status == Status.SUCCESS)
                toast.value =
                    "registration is successful!! Your password is ${resource.data?.result?.password}"
            else if (resource.status == Status.ERROR)
                resource.data?.error?.message?.let { toast.value = it }
        }
    }
}