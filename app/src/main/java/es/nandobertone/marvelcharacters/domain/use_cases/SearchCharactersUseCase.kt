package es.nandobertone.marvelcharacters.domain.use_cases

import es.nandobertone.marvelcharacters.domain.model.Character
import es.nandobertone.marvelcharacters.domain.repository.MarvelRepository
import es.nandobertone.marvelcharacters.util.Response
import es.nandobertone.marvelcharacters.util.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository:MarvelRepository
){
    operator fun invoke(search:String):Flow<Response<List<Character>>> = flow {
        try {
            emit(Response.Loading())
            val list = repository.getAllSearchedCharacters(search).data.results.map {
                it.toCharacter()
            }
            emit(Success(list))
        }
        catch (e:HttpException){
            emit(Response.Error(e.printStackTrace().toString()))
        }
        catch (e:IOException){
            emit(Response.Error(e.printStackTrace().toString()))
        }
    }
}