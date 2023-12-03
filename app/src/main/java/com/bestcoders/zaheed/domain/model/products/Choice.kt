package com.bestcoders.zaheed.domain.model.products

data class Choice(
    val choices: List<SubChoice>,
    val id: Int,
    val name: String
)