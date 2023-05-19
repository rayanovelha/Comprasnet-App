package com.siasg.comprasnet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.siasg.comprasnet.domain.PostgrestMessage
import com.siasg.comprasnet.repository.Resource
import com.siasg.comprasnet.repository.SupabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
import javax.inject.Inject

@HiltViewModel
class SupabaseViewModel @Inject constructor(
    private val repository: SupabaseRepository
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        return repository.login(email, password)
    }

    suspend fun createAccount(email: String, password: String, name: String): Boolean {
        return repository.createAccount(email, password, name)
    }

    suspend fun logout(): Boolean {
        return repository.logout()
    }

    suspend fun deleteAccount(): Boolean {
        return repository.deleteAccount()
    }

    fun isLogged(): Boolean {
        return repository.isLogged()
    }

    suspend fun getCurrentUserInfo(): LiveData<Resource<UserInfo>> {
        return repository.getCurrentUserInfo()
    }

    suspend fun getCurrentUserData(): LiveData<Resource<PostgrestMessage>> {
        return repository.getCurrentUserData()
    }

    suspend fun updateCurrentUserName(uid: String, name: String): Boolean {
        return repository.updateCurrentUserName(uid, name)
    }

}