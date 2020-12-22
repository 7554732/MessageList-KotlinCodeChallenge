package com.fomichev.messagelist_kotlincodechallenge.network

class InputFiles(
    val url: String = "http://audiobooks-3.ru/message_list/",
    val files: List<String> = listOf("01.json", "02.json", "03.json")
)

object NetworkInputDataManager{
    private var _inputFiles =
        InputFiles()
    val inputFiles: InputFiles
        get() = _inputFiles
    val url: String
        get() = _inputFiles.url
    val files: List<String>
        get() = _inputFiles.files

    fun setInputFiles(inputFiles: InputFiles){
        _inputFiles = inputFiles
    }

}