package com.siasg.comprasnet.di

import com.siasg.comprasnet.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseClient {

    @Provides
    @Singleton
    fun provideSupabaseClient() : SupabaseClient{

        val client = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASEURL,
            supabaseKey = BuildConfig.SUPABASEKEY
        ){
            install(GoTrue) {
                customUrl = "https://snonbddbeaodrbodsfar.supabase.co/auth/v1"
            }
            install(Postgrest)
        }

        return client
    }

}