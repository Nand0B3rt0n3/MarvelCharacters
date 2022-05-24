package es.nandobertone.marvelcharacters.data.repository

import es.nandobertone.marvelcharacters.data.data_source.MarvelApi
import es.nandobertone.marvelcharacters.data.data_source.dto.CharactersDTO
import es.nandobertone.marvelcharacters.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api:MarvelApi
):MarvelRepository{
    override suspend fun getAllCharacter(offset: Int): CharactersDTO {
        return api.getAllCharacters(offset = offset.toString())
    }

    override suspend fun getAllSearchedCharacters(search: String): CharactersDTO {
        return api.getAllSearchedCharacters(search = search)
    }
}