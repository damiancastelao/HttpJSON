package edu.dam2.requestwp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.net.URL
import kotlinx.serialization.json.Json

class MainActivity : Activity() {

    // para la etiqueta de los logs
    val LOGTAG = "requestwp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cuando le damos al boton empezamos una corutina
        button.setOnClickListener { peticionwp() }

    }

    /**
     * Hace una peticion a Wordpress. Utiliza API Request
     */
    fun peticionwp() {
        var respuesta = ""
        // lanza la corutina NO en el hilo principal
        doAsync{
            // peticion a wordpress
            respuesta = URL("http://34.242.231.110/wp5/?rest_route=/wp/v2/posts/1").readText()
            // Accedemos al hilo principal
            uiThread {
                response.text = respuesta
                Log.d(LOGTAG, respuesta)
                longToast("Request performed")
            }
        }
    }

}
