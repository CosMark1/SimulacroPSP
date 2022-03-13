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
    val mensajeRepository: MensajeRepository,
    val retwitRepository: RetwitRepository
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
                    return "Error"
                }
            }
        }
        usuarioRepository.save(usuario)
        return usuario
    }
    // curl --request POST  --header "Content-type:application/json" --data "{\"nombre\":\"Ditto\",\"texto\":\"Ditto\"}" localhost:8083/crearMensaje
    @PostMapping("crearMensaje")
    fun crearMensaje(@RequestBody mensaje: Mensaje): Any {

        usuarioRepository.findAll().forEach {
            if (it.nombre == mensaje.nombre) {
                mensajeRepository.save(mensaje)
                return "Listo"
            }
        }
        return "Error"
    }
    @GetMapping("descargarMensajes")
    fun descargarMensajes(): Any {

        mensajeRepository.findAll().forEach {
            Lista.lista.add(it)
        }
        return Lista
    }
    @GetMapping("retwittear/{mensajeId}")
    fun retwittear(@PathVariable mensajeId : Int): Any {
        val mensajeRetwitteado = mensajeRepository.findById(mensajeId)
        if(mensajeRetwitteado.isPresent){
            mensajeRetwitteado.get().retwits++
            return mensajeRetwitteado
        }
        return "Mensaje no encontrado"
    }

    @GetMapping("Retwittear")
    fun retwittear1(@RequestBody retwit: Retwit):Any{

        mensajeRepository.findAll().forEach{
            retwitRepository.save(it)
        }

        return mensajeRepository.getById(retwit.id)
    }



}