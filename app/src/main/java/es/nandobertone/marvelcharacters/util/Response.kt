package es.nandobertone.marvelcharacters.util

import es.nandobertone.marvelcharacters.domain.model.Character

sealed class Response<T>(val data:T?=null,val message:String?=null){
    class Loading<T>(data: T?=null):Response<T>(data)
    class Success<T>(data: T? = null):Response<T>(data)
    class Error<T>(message: String, data: T?=null):Response<T>(data, message)

}