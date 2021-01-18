package com.example.demo.app

import java.io.File
import java.text.DecimalFormat
import java.util.*

class ClasificadorDistancia {
    var muestra: Tokenizador
    var dicc : MutableList<String> = mutableListOf<String>()
    var ind : TreeMap<String,Double> = TreeMap<String,Double>()
    constructor(muestra:Tokenizador){
        this.muestra =muestra
        for( i in 0 until muestra.tokens.size){
            var sentencia = muestra.tokens[i].clase
            if(dicc.binarySearch(sentencia,0,dicc.size)<0){
                dicc.add(sentencia)
            }
        }
        var i = 0;
        var promedios = Array<Double>(dicc.size){0.0}
        var indices = Array<Int>(dicc.size){0}
        for(k in 0 until muestra.tokens.size){
            i = dicc.binarySearch(muestra.tokens[k].clase,0,dicc.size)
            promedios[i]=muestra.tokens[k].calDisEuclidiana()
            indices[i]++
        }
        for(j in 0 until dicc.size){
            promedios[j]=promedios[j]/indices[j]
            ind.put(dicc[j],promedios[j])
        }
    }

    fun test(src:String):String{
        var lines : List<String> = File(src).readLines()
        var inicio = muestra.tokens.size
        lines.forEach { line ->
            if(!line.equals("")) {
                var datos: Array<String> = line.split(",").toTypedArray()
                var cla = datos[datos.size-1]
                var carga = Array<Double>(datos.size -1){i -> datos[i].toDouble() }
                var clase_r = "p"
                muestra.tokens.add(Patron(carga,cla, "m"))
            }
        }
        var final = muestra.tokens.size

        var cal = 0
        for(i in inicio until final){
            var calificacion = muestra.tokens[i].calDisEuclidiana()
            muestra.tokens[i].clase_r= evaluar(calificacion)

            if(muestra.tokens[i].clase_r.equals(muestra.tokens[i].clase_r)){
                cal++
            }
        }
        cal = (cal * 100)/(final-inicio)
        return "De "+(final-inicio)+" pruebas el porcentaje de clasificacion fue "+cal
    }

    fun evaluar(muestra:Double):String{
        var masbajo = 100.0
        var indice = -1
        var puntaje = Array<Double>(dicc.size){0.0}
        for (i in 0 until dicc.size){
            puntaje[i] = Math.abs(ind.get(dicc[i])?:0.minus(muestra))
            if (puntaje[i]<masbajo){
                masbajo=puntaje[i]
                indice=i
            }
        }
        return dicc[indice]
    }

}