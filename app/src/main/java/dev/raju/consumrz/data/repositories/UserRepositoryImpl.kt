package dev.raju.consumrz.data.repositories

import coil.network.HttpException
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.data.local.models.AuthResponse
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.utils.Resource
import java.io.IOException

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
class UserRepositoryImpl(
    private val preferences: AuthPreferences
) : UserRepository {

    override suspend fun isUserLoggedIn(): Resource<Unit> {
        return try {
            if (preferences.getAuthToken().getOrDefault(emptySet()).isNullOrEmpty()) {
                Resource.Error("User is not logged In")
            } else {
                Resource.Success(Unit)
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun login(loginRequest: AuthRequest): Resource<Unit> {
        return try {
            val response = AuthResponse("auth_token")//userDao.loginUser(loginRequest)
            preferences.saveAuthToken(response.token)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }
}