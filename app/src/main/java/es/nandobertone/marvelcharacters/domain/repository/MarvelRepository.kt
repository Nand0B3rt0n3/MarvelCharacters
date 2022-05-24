package es.nandobertone.marvelcharacters.domain.repository

import es.nandobertone.marvelcharacters.data.data_source.dto.CharactersDTO

interface MarvelRepository {
    suspend fun getAllCharacter(offset:Int):CharactersDTO
    suspend fun getAllSearchedCharacters(search:String):CharactersDTO
}