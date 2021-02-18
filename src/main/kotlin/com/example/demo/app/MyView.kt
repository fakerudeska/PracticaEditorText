package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.FileWriter
import java.lang.IllegalStateException
import java.nio.file.Files

class MyView : View(){
    // Por fin va el FMXL
    override val root: BorderPane by fxml()
    val controller: MyController by inject()
    // Observables
    private val btnSave: Button by fxid()
    private val btnCerca: Button by fxid()
    private val btnCrea: Button by fxid()
    private val textAreaEdit: TextArea by fxid()
    private val lblInfo: Label by fxid()
    private var path: String = ""


    init {
        btnSave.action {
            desaText(path)
        }
        btnCerca.action {
            openFile()
        }
        btnCrea.action {
            creaText()
        }

    }

    private fun creaText(){
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Texto", "*.txt"),
                                            FileChooser.ExtensionFilter("All Files", "*.*"))

        var file = fileChooser.showSaveDialog(null)
        var textoFichero: String = textAreaEdit.text.toString()
        try {
            file.printWriter().use { out ->
                out.println(textoFichero)
            }
            lblInfo.text = "Arxiu creat correctament."
        }catch (ex:IllegalStateException){
            println("ERROR: Fitxer no desat")
        }
    }

    private fun desaText(path: String){
        try {
            val fileOut = FileWriter(path)
            fileOut.write(textAreaEdit.text)
            fileOut.close()
            lblInfo.text = "Arxiu desat correctament."
        }catch(ex: Exception){
            print(ex.message)
        }
    }


    fun openFile() : File? {
        val fileChooser = FileChooser()
        fileChooser.title = "Obre:"

        fileChooser.initialDirectory = File("C:\\Users\\Administrador\\Desktop")
        fileChooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Texto", "*.txt")
        )
        val selectedFile: File? = fileChooser.showOpenDialog(currentWindow)
        if (selectedFile != null) {
            lblInfo.text = ""
            textAreaEdit.text = ""
            textAreaEdit.text = selectedFile.inputStream().readBytes().toString(Charsets.UTF_8)
            path = selectedFile.toString()
        }
        return selectedFile

    }

}