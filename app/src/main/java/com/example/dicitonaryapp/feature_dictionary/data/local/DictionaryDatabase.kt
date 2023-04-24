package com.example.dicitonaryapp.feature_dictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.feature_dictionary.data.local.dao.WordInfoDao
import com.example.dicitonaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.dictionary.feature_dictionary.data.local.Converters
import com.example.dictionary.feature_dictionary.data.util.GsonParser
import com.google.gson.Gson

@Database(entities = [WordInfoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract val dao: WordInfoDao

    companion object {
        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DictionaryDatabase::class.java,
                            "dictionary_database"
                        )
                            .addTypeConverter(Converters(GsonParser(Gson())))
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}