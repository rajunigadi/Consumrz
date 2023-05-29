package dev.raju.domain.enitities

/**
 * Created by Rajashekhar Vanahalli on 26 May, 2023
 */
data class SignInParams(val email: String, val password: String) {
    companion object {
        fun from(map: Map<String, String>) = object {
            val email by map
            val password by map

            val data = SignInParams(email, password)
        }.data
    }
}