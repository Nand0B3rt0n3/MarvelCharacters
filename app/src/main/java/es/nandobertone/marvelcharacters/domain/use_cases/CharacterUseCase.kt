package es.nandobertone.marvelcharacters.domain.use_cases

import es.nandobertone.marvelcharacters.domain.model.Character
import es.nandobertone.marvelcharacters.domain.repository.MarvelRepository
import es.nandobertone.marvelcharacters.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val repository:MarvelRepository
){
    operator fun invoke(offset:Int) : Flow<Response<List<Character>>> = flow{
        try{
            emit(Response.Loading())
            val list = repository.getAllCharacter(offset=offset).data.results.map {
                it.toCharacter()
            }
            emit(Response.Success(list))
        }
        catch (e:HttpException){
            emit (Response.Error(e.printStackTrace().toString()))
        }
        catch (e:IOException){
            emit(Response.Error(e.printStackTrace().toString()))
        }
    }
}