package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Usuario (var nombre: String, var password:String){

    @Id
    @GeneratedValue
    var id = 0
    var token = generarToken()
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    fun generarToken(): String {
        var palabra = ""
        repeat(3) {
            val letra = 'a'..'z'
            val letras = letra.random()
            palabra += letras
        }

        return palabra
    }
}
