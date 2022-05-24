package es.nandobertone.marvelcharacters.ui.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.nandobertone.marvelcharacters.domain.use_cases.CharactersUseCase
import es.nandobertone.marvelcharacters.domain.use_cases.SearchCharactersUseCase
import es.nandobertone.marvelcharacters.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCase : CharactersUseCase,
    private val searchCharactersUseCase : SearchCharactersUseCase
):ViewModel(){

    private val marvelValue = MutableStateFlow(MarvelListState())
    var _marvelValue : StateFlow<MarvelListState> = marvelValue

    fun getAllCharactersData(offset:Int) = viewModelScope.launch(Dispatchers.IO) {
        charactersUseCase(offset = offset).collect{
            when(it){
                is Response.Success ->{
                    marvelValue.value = MarvelListState(characterList = it.data?: emptyList())
                }
                is Response.Loading ->{
                    marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error ->{
                    marvelValue.value = MarvelListState(error = it.message?:"Parece algo ha fallado")
                }
            }
        }
    }
    fun getSearchedCharacters(search:String)=viewModelScope.launch(Dispatchers.IO){
        searchCharactersUseCase.invoke(search=search).collect {
            when(it){
                is Response.Success ->{
                    marvelValue.value = MarvelListState(characterList = (it.data?: emptyList()))
                }
                is Response.Loading ->{
                    marvelValue.value = MarvelListState(isLoading = true)
                }
                is Response.Error ->{
                    marvelValue.value = MarvelListState(error = it.message?:"Parece algo ha fallado")
                }
            }
        }
    }
}