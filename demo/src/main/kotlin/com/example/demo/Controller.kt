package com.example.demo
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import java.security.MessageDigest
import javax.crypto.BadPaddingException

@RestController
class Controller(
    val usuarioRepository: UsuarioRepository,
    val mensajeRepository: MensajeRepository

) {


    @GetMapping("mostrarUsuarios")
    fun mostrarUsuario(): Any {
        return usuarioRepository.findAll()
    }

    // curl --request POST  --header "Content-type:application/json" --data "{\"nombre\":\"Ditto\",\"password\":\"Ditto\"}" localhost:8083/crearUsuario
    @PostMapping("crearUsuario")
    fun crearUsuario(@RequestBody usuario: Usuario): Any {

        usuarioRepository.findAll().forEach {
            if (it.nombre == usuario.nombre) {
                if (it.password == usuario.password) {
                    return it.token

                } else {
                    return Error(1,"Contraseña incorrecta")
                }
            }
        }
        usuarioRepository.save(usuario)
        return usuario.token
    }

    @GetMapping("monstrarMensajes/{token}")
    fun monstrarMensajes(@PathVariable token : String): Any {
        usuarioRepository.findAll().forEach{ user ->
            if(user.token == token){
                mensajeRepository.findAll().forEach {
                    Lista.lista.add(it)
                }
                return Lista
            }
        }
        return "Error en el token"
    }
    @GetMapping("descargarMensajes")
    fun descargarMensajes(): Any {
        mensajeRepository.findAll().forEach {
            Lista.lista.add(it)
        }
        return Lista

    }
    // curl --request POST  --header "Content-type:application/json" --data "{\"token\":\"cod\",\"texto\":\"Ditto\"}" localhost:8083/crearMensaje
    @PostMapping("crearMensaje")
    fun crearMensaje(@RequestBody mensaje: Mensaje): Any {

        usuarioRepository.findAll().forEach {
            if (it.token == mensaje.token) {
                mensajeRepository.save(mensaje)
                return "Listo"
            }
        }
        return "Error"
    }



    @GetMapping("retwittear/{mensajeId}")
    fun retwittear(@PathVariable mensajeId: Int): Any {
        val mensajeRetwitteado = mensajeRepository.findById(mensajeId)
        if (mensajeRetwitteado.isPresent) {
            mensajeRetwitteado.get().retwits++
            return mensajeRetwitteado
        }
        return "Mensaje no encontrado"
    }

    //curl --request GET  --header "Content-type:application/json" --data "{\"token\":\"Ditto\",\"texto\":\"Ditto\"}" localhost:8083/retwittear
    @GetMapping("retwittear")
    fun retwittear1(@RequestBody mensaje: Mensaje): Any {

        mensajeRepository.findAll().forEach {

                if (it.texto == mensaje.texto) {
                    mensaje.idd = it.idd
                    val mensajeRetwitteado = mensajeRepository.findById(mensaje.idd)
                    if (mensajeRetwitteado.isPresent) {
                        mensajeRetwitteado.get().retwits++
                        mensajeRepository.save(it)
                        return mensajeRetwitteado
                    }
                    return "Mensaje no encontrado"
                }
            }

        return mensajeRepository.getById(mensaje.idd)
    }
    //curl --request GET  --header "Content-type:application/json" --data "{\"token\":\"rsl\",\"id\": 3}" localhost:8083/retwittearCastellano
    @GetMapping("retwittearCastellano")
    fun retwittearCastellano(@RequestBody retwit: Retwit): Any {

        if(mensajeRepository.findById(retwit.id).isPresent){
            mensajeRepository.getById(retwit.id).retwits++
            val texto = mensajeRepository.getById(retwit.id).texto
            mensajeRepository.save(Mensaje(retwit.token,texto))
            return mensajeRepository.getById(retwit.id)
        }
        return "Error"
    }


}