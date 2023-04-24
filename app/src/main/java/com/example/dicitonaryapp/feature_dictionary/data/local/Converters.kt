package com.example.dictionary.feature_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionary.feature_dictionary.data.util.JsonParser
import com.example.dicitonaryapp.feature_dictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(private val jsonParser: JsonParser) {
    @TypeConverter
    fun fromMeaningListToJson(meaningList: List<Meaning>): String {
        return jsonParser.toJson(meaningList, object : TypeToken<ArrayList<Meaning>>() {}.type)
            ?: "[]"
    }

    @TypeConverter
    fun fromMeaningJsonToMeaningList(jsonString: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(json = jsonString,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        )
    }
}