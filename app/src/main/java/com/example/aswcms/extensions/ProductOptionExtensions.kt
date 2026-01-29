package com.example.aswcms.extensions

import com.example.aswcms.domain.models.ProductOption

fun ProductOption.toOptionChoicesHeadline(): String {
    val choiceNames = this.choices.map { choice -> choice.name }
    return choiceNames.joinToString(separator = ",")
}