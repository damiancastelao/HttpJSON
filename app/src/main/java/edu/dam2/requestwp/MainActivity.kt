package edu.dam2.requestwp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.net.URL
// libreria para parsear JSON
import org.json.JSONArray

class MainActivity : Activity() {
    // para la etiqueta de los logs
    val LOGTAG = "peticionwp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cuando le damos al boton empezamos una corutina
        button.setOnClickListener {
            // visualizamos una barra de progreso para informar al usario
            // que estamos haciendo algo en segundo plano
            progressBar.visibility = VISIBLE
            peticionwp()
        }

    }

    /**
     * Hace una peticion a un servidor que utiliza API Rest
     */
    fun peticionwp() {
        // lanza la corutina NO en el hilo principal
        // 'high order function' doAsync(parametro funcion)
        // no necesitamos los parentesis, ponemos la funcion parametro dentro de las llaves
        doAsync {
            // capturamos los errores de la peticion
            try {
                // peticion a un servidor rest que devuelve un json generico
                val respuesta = URL("https://jsonplaceholder.typicode.com/posts").readText()
                // parsing data
                // sabemos que recibimos un array de objetos JSON
                val miJSONArray = JSONArray(respuesta)
                // recorremos el Array
                for (jsonIndex in 0..(miJSONArray.length() - 1)) {
                    // creamos el objeto 'misDatos' a partir de la clase 'Datos'
                    // asignamos el valor de 'title' en el constructor de la data class 'Datos'
                    val misDatos = Datos(miJSONArray.getJSONObject(jsonIndex).getString("title"))
                    // salida procesada en Logcat
                    Log.d(LOGTAG, misDatos.toString())
                }
                // Accedemos al hilo principal
                uiThread {
                    progressBar.visibility = INVISIBLE
                    // rellenamos nuestro TextView con la respuesta sin procesar
                    response.text = respuesta
                    // Log.d(LOGTAG, respuesta)
                    longToast("Request performed")
                }
            // Si algo va mal lo capturamos
            } catch (e: Exception) {
                uiThread {
                    progressBar.visibility = INVISIBLE
                    longToast("Something go wrong: $e")
                }
            }
        }
    }

}
