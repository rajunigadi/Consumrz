package dev.raju.domain.usecases

import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.enitities.User
import dev.raju.domain.repositories.UserRepository
import dev.raju.domain.utils.DispatcherProvider
import dev.raju.domain.utils.ErrorCodable
import dev.raju.domain.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface UserUseCase {
    suspend fun checkLogin(): Flow<UiState<Unit>>
    suspend fun signIn(params: SignInParams): Flow<UiState<User>>
    suspend fun register(params: SignInParams): Flow<UiState<Unit>>
}

class UserUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : UserUseCase {
    override suspend fun checkLogin(): Flow<UiState<Unit>> {
        return flow {
            emit(UiState.Loading)
            try {
                val response = repository.checkLogin()
                if(response.data != null) {
                    emit(UiState.Success(response.data))
                } else if(!response.errors.isNullOrEmpty()) {
                    emit(UiState.Failure(response.errors))
                } else {
                    emit(UiState.Failure(ErrorCodable.defaultErrors()))
                }
            } catch (e: Exception) {
                emit(UiState.Failure(ErrorCodable.defaultErrors(e)))
            }
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun signIn(params: SignInParams): Flow<UiState<User>> {
        return flow {
            try {
                val response = repository.signIn(params)
                if(response.data != null) {
                    emit(UiState.Success(response.data))
                } else if(!response.errors.isNullOrEmpty()) {
                    emit(UiState.Failure(response.errors))
                } else {
                    emit(UiState.Failure(ErrorCodable.defaultErrors()))
                }
            } catch (e: Exception) {
                emit(UiState.Failure(ErrorCodable.defaultErrors(e)))
            }
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun register(params: SignInParams): Flow<UiState<Unit>> {
        return flow {
            try {
                val response = repository.register(params)
                if(response.data != null) {
                    emit(UiState.Success(response.data))
                } else if(!response.errors.isNullOrEmpty()) {
                    emit(UiState.Failure(response.errors))
                } else {
                    emit(UiState.Failure(ErrorCodable.defaultErrors()))
                }
            } catch (e: Exception) {
                emit(UiState.Failure(ErrorCodable.defaultErrors(e)))
            }
        }.flowOn(dispatcherProvider.io)
    }
}