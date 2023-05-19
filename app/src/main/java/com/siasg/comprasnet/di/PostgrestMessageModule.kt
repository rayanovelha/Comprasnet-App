package com.siasg.comprasnet.di

import com.siasg.comprasnet.domain.PostgrestMessageApi
import com.siasg.comprasnet.domain.PostgrestMessageApiImpl

object PostgrestMessageModule {
    fun providePostgrestMessageApi(): PostgrestMessageApi {
        return PostgrestMessageApiImpl()
    }
}