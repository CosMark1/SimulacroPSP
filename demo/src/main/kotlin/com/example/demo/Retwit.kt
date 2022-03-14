package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


data class Retwit (var token :String, var id:Int) {

    var time = System.currentTimeMillis()
    override fun toString(): String {
        val gson= Gson()
        return gson.toJson(this)
    }

}