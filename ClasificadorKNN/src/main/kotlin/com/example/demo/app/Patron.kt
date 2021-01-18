package com.example.demo.app

public class Patron {
    var clase : String
    var clase_r : String
    var datos : Array<Double>

    constructor(n: Int){
        clase=""
        clase_r =""
        datos = Array<Double>(n){0.0}
    }

    constructor(mdatos : Array<Double>, clase : String, clase_r : String){
        this.clase=clase
        this.clase_r = clase_r
        datos = mdatos
    }

    fun calDisEuclidiana():Double{
        var sumatoria = 0.0
        for (i in datos.indices){
            sumatoria += Math.pow(datos[i],2.0)
        }
        sumatoria = Math.sqrt(sumatoria)

        return sumatoria
    }
}
