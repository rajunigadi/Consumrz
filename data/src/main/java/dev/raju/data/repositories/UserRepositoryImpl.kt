package dev.raju.data.repositories

import dev.raju.domain.utils.KEY_LOGGED_IN
import dev.raju.data.local.UserDao
import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.enitities.User
import dev.raju.domain.repositories.DatastoreRepository
import dev.raju.domain.repositories.UserRepository
import dev.raju.domain.utils.ErrorCodable
import dev.raju.domain.utils.ResponseCodable
import kotlinx.coroutines.delay

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val datastoreRepository: DatastoreRepository
) : UserRepository {

    override suspend fun checkLogin(): ResponseCodable<Unit> {
        return try {
            delay(2000)
            val isLoggedIn = datastoreRepository.getBoolean(KEY_LOGGED_IN).getOrDefault(false)
            if (isLoggedIn) {
                ResponseCodable(data = Unit)
            } else {
                ResponseCodable(
                    errors = arrayOf(
                        ErrorCodable(
                            errorCode = 200,
                            errorMessage = "User is not logged in"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong"
                    )
                )
            )
        }
    }

    override suspend fun signIn(params: SignInParams): ResponseCodable<User> {
        return try {
            val users = userDao.findUserByEmail(email = params.email)
            if (users?.isNotEmpty() == true && users[0].email == params.email) {
                datastoreRepository.putBoolean(KEY_LOGGED_IN, true)
                ResponseCodable(data = users[0])
            } else {
                datastoreRepository.putBoolean(KEY_LOGGED_IN, false)
                ResponseCodable(
                    errors = arrayOf(
                        ErrorCodable(
                            errorCode = 200,
                            errorMessage = "Unable to sign in to user"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong"
                    )
                )
            )
        }
    }

    override suspend fun register(params: SignInParams): ResponseCodable<Unit> {
        return try {
            val rowId = userDao.addUser(User(email = params.email, password = params.password))
            datastoreRepository.putBoolean(KEY_LOGGED_IN, rowId > 0)
            if(rowId > 0) {
                ResponseCodable(data = Unit)
            } else {
                ResponseCodable(errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Unable to register user"
                    )
                ))
            }
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong"
                    )
                )
            )
        }
    }
}