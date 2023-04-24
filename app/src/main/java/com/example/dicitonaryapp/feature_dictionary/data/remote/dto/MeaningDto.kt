package com.example.dicitonaryapp.feature_dictionary.data.remote.dto

import com.example.dicitonaryapp.feature_dictionary.domain.model.Meaning
import com.plcoding.dictionary.feature_dictionary.data.remote.dto.DefinitionDto


data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
) {
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}