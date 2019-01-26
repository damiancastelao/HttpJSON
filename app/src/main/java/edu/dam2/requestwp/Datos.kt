package edu.dam2.requestwp

data class Datos(val titulo : String){
    // personalizamos to String
    override fun toString(): String {
        return "El título es: $titulo"
    }
}
