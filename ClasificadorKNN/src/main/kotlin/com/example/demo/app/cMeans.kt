package com.example.demo.app

import java.util.*
import kotlin.math.abs

class cMeans {
    var muestra: Tokenizador
    var c : Int
    var distancias : Array<Double>
    var promedios : Array<Double>
    var giro : Int
    constructor(muestra:Tokenizador,c:Int, porcentaje: Double){
        giro =0
        this.muestra = muestra
        this.c = c
        distancias = Array(muestra.tokens.size){i: Int -> muestra.tokens[i].calDisEuclidiana() }
        var promedio = puntos_iniciales(c)
        promedios = Array<Double>(c){0.0}
        for( i in 0 until c){
            promedios[i]=distancias[promedio[i]]
        }
        asignarclases()
        giro++
        println("Giro: $giro")
        automatizar(porcentaje)
        println("finalizado automatizado")
    }
    fun automatizar(porcentaje : Double){
        var anterior = promedios;
        var aciertos = 0;
        var actuales = 0;
        aciertos = (c*(0.80)).toInt();

        do {
            actuales=0
            anterior=promedios
            ciclo()
            for(i in 0 until c){
                if(calc_error(i,porcentaje,anterior[i]))
                    actuales++
            }
        }
        while (actuales<aciertos)
    }

    fun calc_error(indice: Int, porcentaje: Double, anterior:Double):Boolean{
        var relativo = abs(promedio_clase(indice)-anterior)
        var absoluto = relativo/anterior
        var rporcentaje = absoluto*100
        if(rporcentaje<=porcentaje)
            return true
        return false
    }

    fun ciclo(){
        recalculos()
        asignarclases()
        giro++
        println("Giro: $giro")
    }

    fun puntos_iniciales(c: Int) : Array<Int>{

        var saltos = muestra.tokens.size / c
        var iniciales = Array<Int>(c){0}
        for(i in 0 until c){
            iniciales[i]=saltos*i
        }
        return iniciales;
    }

    fun calculo_abs(indice: Double, punto : Double):Double{
        return Math.sqrt(Math.pow(indice-punto,2.0))
    }

    fun mas_cerca(indice_valor:Int):Int{
        var muestra = Array<Double>(c){0.0}
        for (i in 0 until c){
            muestra[i]=calculo_abs(distancias[indice_valor],promedios[i])
        }
        var lower = 0.0;
        var ilower = 0;
        lower = muestra[0]
        for(i in 1 until c){
            if(lower>muestra[i]){
                lower = muestra[i]
                ilower =i
            }
        }
        return ilower
    }

    fun asignacion(indice_valor: Int){
        var clase = mas_cerca(indice_valor)
        muestra.tokens[indice_valor].clase_r=clase.toString()
    }

    fun asignarclases(){
        for (i in 0 until muestra.tokens.size)
            asignacion(i)
    }

    fun recalculos(){
        var recal = Array<Double>(c){0.0}
        for (i in 0 until c){
            recal[i]=promedio_clase(i)
        }
        promedios= recal
    }

    fun promedio_clase(clase:Int):Double{
        var acumulador = 0.0
        var contador = 0;
        for(i in 0 until muestra.tokens.size ){
            if(muestra.tokens[i].clase_r.equals(clase.toString())){
                acumulador+=distancias[i]
                contador++
            }
        }

        var resultado = 0.0
        resultado = acumulador/contador
        return resultado
    }




}