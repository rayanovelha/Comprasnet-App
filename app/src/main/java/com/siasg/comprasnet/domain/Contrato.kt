package com.siasg.comprasnet.domain

import kotlinx.serialization.Serializable

@Serializable
data class Contrato(
    val categoria: String,
    val codigo_compra: String,
    val codigo_contrato: String,
    val codigo_tipo: Int,
    val data_assinatura: String,
    val data_publicacao: String,
    val fornecedor_cnpj_cpf_idgener: String,
    val fornecedor_nome: String,
    val fornecedor_tipo: String,
    val fundamento_legal: String?,
    val id: Int,
    val informacao_complementar: String,
    val licitacao_numero: String,
    val modalidade: String,
    val modalidade_codigo: String,
    val num_parcelas: Int,
    val numero: String,
    val objeto: String,
    val orgao_codigo: Int,
    val orgao_nome: String,
    val processo: String,
    val receita_despesa: String,
    val tipo: String,
    val unidade_codigo: Int,
    val unidade_compra: Int,
    val unidade_nome: String,
    val unidade_nome_resumido: String,
    val unidade_origem_codigo: String,
    val unidade_origem_nome: String,
    val valor_acumulado: Double,
    val valor_global: Double,
    val valor_inicial: Double,
    val valor_parcela: Double,
    val vigencia_fim: String,
    val vigencia_inicio: String
)
