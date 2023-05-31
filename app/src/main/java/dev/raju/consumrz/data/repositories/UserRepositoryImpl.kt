package dev.raju.consumrz.data.repositories

import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.data.local.models.AuthRequest
import dev.raju.consumrz.domain.model.User
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.utils.Resource
import java.io.IOException

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val preferences: AuthPreferences
) : UserRepository {

    override suspend fun isUserLoggedIn(): Resource<Unit> {
        return try {
            if (preferences.getEmail().getOrDefault("").isEmpty()) {
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
                preferences.saveEmail(users[0].email)
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

    override suspend fun logout(): Resource<Unit> {
        return try {
            preferences.clearPreferences()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun register(user: User): Resource<Unit> {
        return try {
            val rowId = userDao.addUser(user)
            if(rowId > 0) {
                preferences.saveEmail(user.email)
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