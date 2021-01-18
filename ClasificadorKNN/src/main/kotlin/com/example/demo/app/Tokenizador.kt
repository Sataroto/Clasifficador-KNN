package com.example.demo.app

import java.io.File

public class Tokenizador{
    var tokens : MutableList<Patron> = mutableListOf<Patron>()
    constructor(src :String) {
        var lines : List<String> = File(src).readLines()
        lines.forEach { line ->
            if(!line.equals("")) {
                var muestra: Array<String> = line.split(",").toTypedArray()
            // configuracion iris
                var cla = muestra[muestra.size-1]
                var carga = Array<Double>(muestra.size-1){i -> muestra[i].toDouble() }
                tokens.add(Patron(carga,cla, "m"))
            }
        }
        println(tokens.size)
    }

}