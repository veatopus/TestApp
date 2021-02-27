package kg.bishkek.ruslan.data.models.regisration

import java.util.*


data class RegistrationModel(
    var first_name: String,
    var first_phone: String,
    var gender: Int,
    var last_name: String,
    var nationality: Int,
    var question: Int,
    var response: String,
    var second_name: String,
    var second_phone: String,
    var sms_code: String,
    var traffic_source: Int,
    var u_date: Date,
    var system: Int = 1
)

data class RegistrationResult(
    var code: Int? = null,
    var error: Error? = null,
    var result: Result? = null
) {
    override fun toString(): String {
        return "RegistrationResult(code=$code, error=$error, result=$result)"
    }
}

data class Error(
    var code: Int? = null,
    var message: String? = null
) {
    override fun toString(): String {
        return "Error(code=$code, message=$message)"
    }
}

data class Result(
    var code: Int? = null,
    var id: String? = null,
    var login: String? = null,
    var message: String? = null,
    var password: String? = null
) {
    override fun toString(): String {
        return "Result(code=$code, id=$id, login=$login, message=$message, password=$password)"
    }
}