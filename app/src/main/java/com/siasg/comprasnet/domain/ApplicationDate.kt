package com.siasg.comprasnet.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ApplicationDate {

    private val date_hoje = LocalDate.now().toEpochDay()
    private val vencimento_30 = LocalDate.ofEpochDay(date_hoje + 29)
    private val vencimento_60 = LocalDate.ofEpochDay(date_hoje + 59)
    private val vencimento_90 = LocalDate.ofEpochDay(date_hoje + 89)
    private val vencimento_180 = LocalDate.ofEpochDay(date_hoje + 179)
    private val vencimento_181 = LocalDate.ofEpochDay(date_hoje + 180)


    fun getDate_Hoje(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = LocalDateTime.now().format(formatter)
        return formattedDate
    }

    fun getVencimento_30(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = vencimento_30.format(formatter)
        return formattedDate
    }

    fun getVencimento_60(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = vencimento_60.format(formatter)
        return formattedDate
    }

    fun getVencimento_90(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = vencimento_90.format(formatter)
        return formattedDate
    }

    fun getVencimento_180(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = vencimento_180.format(formatter)
        return formattedDate
    }

    fun getVencimento_181(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = vencimento_181.format(formatter)
        return formattedDate
    }

}