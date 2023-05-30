package dev.raju.consumrz.data.repositories

import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.domain.model.User
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.utils.Resource
import java.io.IOException

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val preferences: AuthPreferences
) : UserRepository {

    override suspend fun isUserLoggedIn(): Resource<Unit> {
        return try {
            if (preferences.getAuthToken().getOrDefault(emptySet()).isEmpty()) {
                Resource.Error("User is not logged In")
            } else {
                Resource.Success(Unit)
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun login(loginRequest: AuthRequest): Resource<Unit> {
        return try {
            val users = userDao.findUserByEmail(email = loginRequest.email)
            if ((users?.isNotEmpty() == true) && (users[0].email == loginRequest.email)) {
                preferences.saveAuthToken(users[0].email)
                Resource.Success(Unit)
            } else {
                Resource.Error("Unable to sign in with user credentials")
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun register(registerRequest: AuthRequest): Resource<Unit> {
        return try {
            val rowId = userDao.addUser(User(email = registerRequest.email, password = registerRequest.password))
            if(rowId > 0) {
                preferences.saveAuthToken(registerRequest.email)
                Resource.Success(Unit)
            } else {
                Resource.Error("Unable to register with user credentials")
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }
}