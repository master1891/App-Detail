package com.nels.master.pruebaopenpay.shared

sealed class DataOrError<out T> {

    data class Data<T>(val value: T) : DataOrError<T>()

    data class Error<T>(val message: String) : DataOrError<T>() {
        constructor(e: Exception) : this(e.message ?: e.javaClass.simpleName)
    }

    fun <S> mapContent(mapping: (T) -> S): DataOrError<S> = when (this) {
        is Data -> Data(mapping(this.value))
        is Error -> Error(this.message)
    }

    /**
     * Si este valor es de tipo DataOrError.Data, evalua la funcion que recibe como argumento:
     * Si el resultado es nulo, regresa el mismo objeto 'Data'
     * Si el resultado es un String no nulo, se utiliza este valor para regresar un objeto 'Error'
     *
     * Si este valor es de tipo DataOrError.Error, devuelve el mismo objeto.
     * */
    fun toErrorIf(getError: (T) -> String?): DataOrError<T> {
        return when (this) {
            is Data -> getError(value)?.let { Error(it) } ?: this
            is Error -> this
        }
    }

    fun toErrorIf(isError: (T) -> Boolean, getErrorMessage: (T) -> String): DataOrError<T> {
        return when (this) {
            is Data -> if (isError(value)) Error(getErrorMessage(value)) else this
            is Error -> this
        }
    }
}