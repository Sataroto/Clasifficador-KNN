package com.example.demo.view

import com.example.demo.app.ClasificadorDistancia
import com.example.demo.app.ClasificadorKNN
import com.example.demo.app.Tokenizador
import com.example.demo.app.cMeans
import javafx.stage.FileChooser
import tornadofx.*

class MainView : View("Probando uno dos") {
    var extensiones  = mutableListOf<String>()
    override val root = hbox {
        extensiones.add("*.txt")
        extensiones.add("*.data")
       var dir = chooseFile("Escoje el dataset de entrenamiento", arrayOf<FileChooser.ExtensionFilter>(FileChooser.ExtensionFilter("Text Files", extensiones)))
        var seleccion = Tokenizador(dir[0].path)
        var ejercicio = cMeans(seleccion,3,0.34)
        /*var clad = ClasificadorDistancia(seleccion)
        var intencion = ClasificadorKNN(seleccion)
        var intento = cMeans(seleccion,6)
        var dirt = chooseFile("Escoje el dataset de test", arrayOf<FileChooser.ExtensionFilter>(FileChooser.ExtensionFilter("Text Files", extensiones)))
        var equisde = clad.test(dirt[0].path)
        label("El metodo de distancia "+equisde)
        label("El metodo de knn "+intencion.clasificarknn(dirt[0].path,5))*/


    }
}
