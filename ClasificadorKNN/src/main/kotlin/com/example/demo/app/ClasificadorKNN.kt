package com.example.demo.app

import jdk.nashorn.internal.parser.Token
import java.io.File
import java.text.DecimalFormat
import java.util.*

class ClasificadorKNN {
    var muestra: Tokenizador
    var dicc : MutableList<String> = mutableListOf<String>()
    var training: TreeMap<Double,String> = TreeMap<Double,String>()

    constructor(muestra: Tokenizador){
        var formato = DecimalFormat("#.###")
        this.muestra = muestra
        for( i in 0 until muestra.tokens.size){
            var puntaje = formato.format(muestra.tokens[i].calDisEuclidiana()).toDouble()
            var sentencia = muestra.tokens[i].clase
            if(dicc.binarySearch(sentencia,0,dicc.size)<0){
                dicc.add(sentencia)
            }
            training.put(puntaje,sentencia)
        }
        println("entrenamiento terminado")
    }

    fun clasificarknn(src:String, vecino:Int):String{
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
        var formato = DecimalFormat("#.###")
        var cal = 0
        for(i in inicio until final){
            var calificacion = formato.format(muestra.tokens[i].calDisEuclidiana()).toDouble()
            muestra.tokens[i].clase_r= retorno(calificacion,vecino)
            if(muestra.tokens[i].clase_r.equals(muestra.tokens[i].clase_r)){
                cal++
            }
        }
        cal = (cal * 100)/(final-inicio)
        return "De "+(final-inicio)+" pruebas el porcentaje de clasificacion fue "+cal
    }

    fun retorno(muestra:Double, vecino: Int):String {
        var vecinos = mutableListOf<String>()
        var lki = 0.0;
        if (vecino % 2 == 0) {
            var coincidencia = training.ceilingKey(muestra)
            for (i in 0 until vecino / 2) {
                training.get(coincidencia)?.let { vecinos.add(it) }
                coincidencia = training.higherKey(coincidencia)
                if (coincidencia != null) {
                    lki = coincidencia;
                } else {
                    coincidencia = lki
                }
            }
            coincidencia = training.ceilingKey(muestra)
            for (i in 0 until vecino / 2) {
                training.get(coincidencia)?.let { vecinos.add(it) }
                coincidencia = training.lowerKey(coincidencia)
                if (coincidencia != null) {
                    lki = coincidencia;
                } else {
                    coincidencia = lki
                }
            }
        } else if (vecino % 2 == 1) {
            var coincidencia = training.ceilingKey(muestra)
            for (i in 0 until (vecino - 1) / 2) {
                training.get(coincidencia)?.let { vecinos.add(it) }
                coincidencia = training.higherKey(coincidencia)
                if (coincidencia != null) {
                    lki = coincidencia;
                } else {
                    coincidencia = lki
                }
            }
            coincidencia = training.ceilingKey(muestra)
            for (i in 0 until (vecino + 1) / 2) {
                training.get(coincidencia)?.let { vecinos.add(it) }
                coincidencia = training.lowerKey(coincidencia)
                if (coincidencia != null) {
                    lki = coincidencia;
                } else {
                    coincidencia = lki
                }
            }
        }

        var coincidencias = Array(dicc.size, { i -> 0 })
        for (i in 0 until vecinos.size) {
            for (j in 0 until dicc.size) {
                if (vecinos[i].equals(dicc[j])) {
                    coincidencias[j]++
                    break
                }
            }
        }
        var biggest = 0
        var idicc = 0
        for (i in 0 until coincidencias.size) {
            if (biggest < coincidencias[i]) {
                biggest = coincidencias[i]
                idicc = i
            }
        }
        var resultado = dicc[idicc]
        return resultado
    }
}