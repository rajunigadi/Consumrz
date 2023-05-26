package dev.raju.domain.usecases

import dev.raju.domain.enitities.LoginState
import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.repositories.UserRepository
import dev.raju.domain.utils.DispatcherProvider
import dev.raju.domain.utils.ResponseCodable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface UserUseCase {
    suspend fun checkLogin(): Flow<ResponseCodable<LoginState>>
    suspend fun signIn(params: SignInParams): Flow<ResponseCodable<LoginState>>
    suspend fun register(params: SignInParams): Flow<ResponseCodable<LoginState>>
}

class UserUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : UserUseCase {
    override suspend fun checkLogin(): Flow<ResponseCodable<LoginState>> {
        return flow {
            try {
                emit(ResponseCodable.Success(repository.checkLogin()))
            } catch (e: Exception) {
                emit(ResponseCodable.Failure(e.message))
            }
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun signIn(params: SignInParams): Flow<ResponseCodable<LoginState>> {
        return flow {
            try {
                emit(ResponseCodable.Success(repository.signIn(params)))
            } catch (e: Exception) {
                emit(ResponseCodable.Failure(e.message))
            }
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun register(params: SignInParams): Flow<ResponseCodable<LoginState>> {
        return flow {
            try {
                emit(ResponseCodable.Success(repository.register(params)))
            } catch (e: Exception) {
                emit(ResponseCodable.Failure(e.message))
            }
        }.flowOn(dispatcherProvider.io)
    }
}