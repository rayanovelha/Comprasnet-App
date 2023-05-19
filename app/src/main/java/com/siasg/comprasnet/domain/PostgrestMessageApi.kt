package com.siasg.comprasnet.domain

import io.github.jan.supabase.postgrest.query.PostgrestBuilder
import kotlinx.datetime.Instant

interface PostgrestMessageApi {

    suspend fun retrieve(uid: String, table: PostgrestBuilder): PostgrestMessage

    suspend fun create(uid: String, createdAt: Instant?, name: String, table: PostgrestBuilder)

    suspend fun delete(uid: String, table: PostgrestBuilder)

    suspend fun updateFavorites(uid: String, list: List<FavoriteContrato>, table: PostgrestBuilder)

    suspend fun updateName(uid: String, name: String, table: PostgrestBuilder)

}

class PostgrestMessageApiImpl(): PostgrestMessageApi{

    override suspend fun retrieve(uid: String, table: PostgrestBuilder): PostgrestMessage {
        return table.select {
            PostgrestMessage::uid eq uid
        }.decodeSingle<PostgrestMessage>()
    }

    override suspend fun create(uid: String, createdAt: Instant?, name: String, table: PostgrestBuilder) {
        table.insert(PostgrestMessage(uid, createdAt, "", name))
    }

    override suspend fun delete(uid: String, table: PostgrestBuilder) {
        table.delete { PostgrestMessage::uid eq uid }
    }

    override suspend fun updateFavorites(uid: String, list: List<FavoriteContrato>, table: PostgrestBuilder) {
        val content = list.joinToString(separator = ",") { it.id }
        table.update(
            {
                PostgrestMessage::favorites setTo content
            }
        ) {
            PostgrestMessage::uid eq uid
        }
    }

    override suspend fun updateName(uid: String, name: String, table: PostgrestBuilder) {
        table.update(
            {
                PostgrestMessage::name setTo name
            }
        ) {
            PostgrestMessage::uid eq uid
        }
    }

}