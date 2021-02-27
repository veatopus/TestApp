package kg.bishkek.ruslan.data.impl

import android.util.Log
import androidx.lifecycle.liveData
import kg.bishkek.ruslan.data.models.RepositoryApi
import kg.bishkek.ruslan.data.models.Resource
import kg.bishkek.ruslan.data.models.auth.LoginModel
import kg.bishkek.ruslan.data.models.regisration.RegistrationModel
import kg.bishkek.ruslan.data.network.apies.RequestApi
import kg.bishkek.ruslan.data.preferences.IdPreferences
import kg.bishkek.ruslan.data.utils.StatusCode
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

/**
 * RepositoryImpl is class implementing RepositoryApi
 * this class for implementation your REST questions
 */
class RepositoryImpl(
    private val api: RequestApi,
    private val idPreferences: IdPreferences
) : RepositoryApi {

    private val bodyForOnlyId: RequestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("code", idPreferences.id.toString())
        .build()

    override fun checkPhone(phone: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val body: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone", phone)
                .build()

            val result = api.checkPhone(body)

            Log.e("fdddsa", "checkPhone: ${result.error}")

            if (result.code == StatusCode.Success.code) {
                if (result.error != null) {
                    emit(Resource.error(result, "number is busy"))
                } else if (result.result != null) {
                    result.result?.id?.let { idPreferences.id = it }
                    emit(Resource.success(result))
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(null, e.message.toString()))
        }
    }

    override fun checkCode(code: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val body: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", idPreferences.id.toString())
                .addFormDataPart("code", code)
                .build()

            val result = api.checkCode(body)

            Log.e(
                "dlkfjsdk",
                "checkCode: ${result.error?.code}\n${result.error?.message}\n${result.result?.code}"
            )

            if (result.code == StatusCode.Success.code) {
                if (result.error != null && result.error?.code == StatusCode.BadRequest.code) {
                    emit(Resource.error(null, result.error?.message.toString()))
                } else if (result.result != null && result.result?.code == StatusCode.Success.code) {
                    emit(Resource.success(result))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }

    override fun getListGender() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val result = api.getListGender(bodyForOnlyId)

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null) {
                    emit(Resource.error(null, result.error?.message.toString()))
                }
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

    override fun getListSecretQuestions() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val result = api.getListSecretQuestions(bodyForOnlyId)

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null) {
                    emit(Resource.error(null, result.error?.message.toString()))
                }
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

    override fun getListTrafficSource() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val result = api.getListTrafficSource(bodyForOnlyId)

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null) {
                    emit(Resource.error(null, result.error?.message.toString()))
                }
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

    override fun getListAvailableCountry() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val result = api.getListAvailableCountry(bodyForOnlyId)

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null) {
                    emit(Resource.error(null, result.error?.message.toString()))
                }
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

    override fun getListNationality() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val result = api.getListNationality(bodyForOnlyId)

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null)
                    emit(Resource.error(null, result.error?.message.toString()))
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

    override fun login(login: String, password: String, uid: String, appid: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))

            try {
                val body = LoginModel(appid, login, password, uid)
                val result = api.login(body)

                if (result.code == StatusCode.Success.code) {
                    if (result.result != null)
                        emit(Resource.success(result))
                    else if (result.error != null) {
                        emit(Resource.error(null, result.error?.message.toString()))
                    }
                }

            } catch (e: Exception) {
                Resource.error(null, e.message.toString())
            }
        }

    override fun registrations(
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
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            val body: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("last_name", lastName)
                .addFormDataPart("first_name", firstName)
                .addFormDataPart("second_name", secondName)
                .addFormDataPart("u_date", uDate)
                .addFormDataPart("gender", gender.toString())
                .addFormDataPart("nationality", nationality.toString())
                .addFormDataPart("first_phone", firstPhone)
                .addFormDataPart("second_phone", secondPhone)
                .addFormDataPart("question", question.toString())
                .addFormDataPart("response", response)
                .addFormDataPart("traffic_source", trafficSource.toString())
                .addFormDataPart("sms_code", smsCode)
                .addFormDataPart("system", "1")
                .build()

            val result = api.registrations(body)

            Log.e("fdsfas", "registrations: $result")

            if (result.code == StatusCode.Success.code) {
                if (result.result != null)
                    emit(Resource.success(result))
                else if (result.error != null) {
                    emit(Resource.error(null, result.error?.message.toString()))
                }
            }

        } catch (e: Exception) {
            Resource.error(null, e.message.toString())
        }
    }

}