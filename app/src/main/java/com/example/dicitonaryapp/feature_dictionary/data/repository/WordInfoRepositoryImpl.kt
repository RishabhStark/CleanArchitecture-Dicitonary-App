package com.example.dicitonaryapp.feature_dictionary.data.repository

import com.example.dicitonaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dicitonaryapp.feature_dictionary.data.remote.dto.WordInfoDto
import com.example.dicitonaryapp.core.util.Error
import com.example.dicitonaryapp.core.util.Loading
import com.example.dicitonaryapp.core.util.Resource
import com.example.dicitonaryapp.core.util.Success
import com.example.dictionary.feature_dictionary.data.local.dao.WordInfoDao
import com.example.dictionary.feature_dictionary.domain.model.WordInfo
import com.example.dicitonaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(private val dao: WordInfoDao, private val api: DictionaryApi) :
    WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Loading())
            val wordInfo = dao.getWordInfos(word = word).map { it.toWordInfo() }
            emit(Loading(wordInfo))
            try {
                val remoteWordInfo: List<WordInfoDto> = api.getWordInfo(word = word)
                // remove the old word info from cache
                dao.deleteWords(remoteWordInfo.map { it.word })
                //insert new word info into cache
                dao.insertWordInfos(remoteWordInfo.map { it.toWordInfoEntity() })
            } catch (e: HttpException) {
                emit(Error("Http Exception occurred", data = wordInfo))
            } catch (io: IOException) {
                emit(Error("IO Exception occurred", data = wordInfo))
            }

            val newWordInfo = dao.getWordInfos(word = word).map { it.toWordInfo() }
            emit(Success(newWordInfo))
        }
    }
}