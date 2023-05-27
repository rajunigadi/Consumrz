package dev.raju.data.repositories

import dev.raju.domain.utils.KEY_LOGGED_IN
import dev.raju.data.local.UserDao
import dev.raju.domain.enitities.LoginState
import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.enitities.User
import dev.raju.domain.repositories.DatastoreRepository
import dev.raju.domain.repositories.UserRepository
import kotlinx.coroutines.delay

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val datastoreRepository: DatastoreRepository
) : UserRepository {

    override suspend fun checkLogin(): LoginState {
        delay(2000)
        return LoginState(datastoreRepository.getBoolean(KEY_LOGGED_IN).getOrDefault(false))
    }

    override suspend fun signIn(params: SignInParams): LoginState {
        val users = userDao.findUserByEmail(email = params.email)
        return if(users?.isNotEmpty() == true && users[0].email == params.email) {
            //datastoreRepository.putBoolean(KEY_LOGGED_IN, true)
            LoginState(true)
        } else {
            //datastoreRepository.putBoolean(KEY_LOGGED_IN, false)
            LoginState(false)
        }
    }

    override suspend fun register(params: SignInParams): LoginState {
        val rowId = userDao.addUser(User(email = params.email, password = params.password))
        //datastoreRepository.putBoolean(KEY_LOGGED_IN, rowId > 0)
        return LoginState(rowId > 0)
    }
}