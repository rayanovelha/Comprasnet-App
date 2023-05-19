package com.siasg.comprasnet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siasg.comprasnet.domain.PostgrestMessage
import com.siasg.comprasnet.domain.PostgrestMessageApiImpl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class SupabaseRepository @Inject constructor() {

    @Inject
    lateinit var client: SupabaseClient

    @Inject
    lateinit var favoriteContratosRepository: FavoriteContratosRepository

    private var supabaseApi = PostgrestMessageApiImpl()

    private val _currentUserInfo = MutableLiveData<Resource<UserInfo>>()
    val currentUserInfo: LiveData<Resource<UserInfo>> = _currentUserInfo

    private val _currentUserData = MutableLiveData<Resource<PostgrestMessage>>()
    val currentUserData: LiveData<Resource<PostgrestMessage>> = _currentUserData

    suspend fun login(email: String, password: String): Boolean {
        return kotlin.runCatching {
            if (isLogged()) {
                return false
            } else {
                client.gotrue.loginWith(Email) {
                    this.email = email
                    this.password = password
                }
            }
        }.onSuccess {
            _currentUserInfo.postValue(Resource(dado = client.gotrue.retrieveUserForCurrentSession()))
        }.onFailure {
            return false
        }.isSuccess
    }

    suspend fun createAccount(email: String, password: String, name: String): Boolean {
        return kotlin.runCatching {
            client.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }.onSuccess {
            supabaseApi.create(
                client.gotrue.retrieveUserForCurrentSession().id,
                client.gotrue.retrieveUserForCurrentSession().createdAt,
                name,
                client.postgrest["userData"]
            )
        }.onFailure {
            return false
        }.isSuccess
    }

    suspend fun logout(): Boolean {
        if (isLogged()) {
            return kotlin.runCatching {
                client.gotrue.invalidateSession()
                favoriteContratosRepository.cleanFavoriteContrato()
            }.onFailure {
                return false
            }.isSuccess
        } else {
            return false
        }
    }

    suspend fun deleteAccount(): Boolean {
        if (isLogged()) {
            val uid = client.gotrue.retrieveUserForCurrentSession().id
            return kotlin.runCatching {
                logout()
                client.gotrue.admin.deleteUser(uid)
            }.onSuccess {
                supabaseApi.delete(uid, client.postgrest["userData"])
            }.onFailure {
                return false
            }.isSuccess
        } else
            return false
    }

    suspend fun getCurrentUserInfo(): LiveData<Resource<UserInfo>> {
        _currentUserInfo.postValue(Resource(dado = client.gotrue.retrieveUserForCurrentSession()))
        return currentUserInfo
    }

    suspend fun getCurrentUserData(): LiveData<Resource<PostgrestMessage>> {
        val result = supabaseApi.retrieve(
            client.gotrue.retrieveUserForCurrentSession().id,
            client.postgrest["userData"]
        )
        _currentUserData.postValue(Resource(dado = result))
        return currentUserData
    }

    suspend fun updateCurrentUserName(uid: String, name: String): Boolean {
        return kotlin.runCatching {
            supabaseApi.updateName(uid = uid, name = name, table = client.postgrest["userData"])
        }.onFailure {
            return false
        }.isSuccess
    }

    fun isLogged(): Boolean {
        return client.gotrue.currentSessionOrNull() != null
    }

}