package com.example.demo.view

import tornadofx.*

class OpenFile : View("Selecciones Archivo") {
    override val root = borderpane {
        button("Press me")
        label("Waiting")
            button("Target Directory") {
                action {
                    var dir = chooseDirectory("Select Target Directory")
                }
            }
    }
}
