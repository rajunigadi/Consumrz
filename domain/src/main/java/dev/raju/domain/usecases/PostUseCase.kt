package dev.raju.domain.usecases

import android.util.Log
import dev.raju.domain.enitities.Comment
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.PostParams
import dev.raju.domain.repositories.PostRepository
import dev.raju.domain.utils.DispatcherProvider
import dev.raju.domain.utils.ErrorCodable
import dev.raju.domain.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface PostUseCase {
    suspend fun getPosts(): Flow<UiState<List<Post>>>
    suspend fun addPost(postParams: PostParams): Flow<UiState<Unit>>
    suspend fun updatePost(post: Post): Flow<UiState<Unit>>
    suspend fun addComment(comment: Comment): Flow<UiState<Unit>>
    suspend fun updateComment(comment: Comment): Flow<UiState<Unit>>
}

class PostUseCaseImpl(
    private val repository: PostRepository,
    private val dispatcherProvider: DispatcherProvider
) : PostUseCase {

    override suspend fun getPosts(): Flow<UiState<List<Post>>> {
        return flow {
            emit(UiState.Loading)
            try {
                val response = repository.getPosts()
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

    override suspend fun addPost(postParams: PostParams): Flow<UiState<Unit>> {
        return flow {
            emit(UiState.Loading)
            try {
                val response = repository.addPost(postParams = postParams)
                Log.d("aarna", "response: $response")
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

    override suspend fun updatePost(post: Post): Flow<UiState<Unit>> {
        return flow {
            try {
                val response = repository.updatePost(post = post)
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

    override suspend fun addComment(comment: Comment): Flow<UiState<Unit>> {
        return flow {
            try {
                val response = repository.addComment(comment = comment)
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

    override suspend fun updateComment(comment: Comment): Flow<UiState<Unit>> {
        return flow {
            try {
                val response = repository.updateComment(comment = comment)
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