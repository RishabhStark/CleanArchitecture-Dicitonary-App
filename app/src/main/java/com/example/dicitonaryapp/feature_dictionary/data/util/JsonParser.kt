package com.example.dictionary.feature_dictionary.data.util

import java.lang.reflect.Type

interface JsonParser {
    fun <T> toJson(obj: T, type: Type): String?
    fun <T> fromJson(json: String, type: Type): T
}