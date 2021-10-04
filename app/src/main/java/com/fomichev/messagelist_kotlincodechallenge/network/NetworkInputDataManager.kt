package com.fomichev.messagelist_kotlincodechallenge.network

class InputFiles(
    val url: String = "http://audiobooks3.ml/message_list/",
    val files: List<String> = listOf("01.json", "02.json", "03.json")
)
interface FileIterator {
    fun hasNextFile(): Boolean
    fun nextFile(): String?
    fun getAllFilesBefore(): List<String>
    fun resetCurrentFilePosition()
}

object NetworkInputDataManager: FileIterator{
    private var _inputFiles =
        InputFiles()
    val inputFiles: InputFiles
        get() = _inputFiles

    val url: String
        get() = _inputFiles.url

    val files: List<String>
        get() = _inputFiles.files

    private var currentPosition = 0

    fun setInputFiles(inputFiles: InputFiles){
        _inputFiles = inputFiles
    }

    override fun hasNextFile(): Boolean {
        return currentPosition < files.size
    }

    override fun nextFile(): String? {
        if(!hasNextFile()) return null
        return files.get(currentPosition++)
    }

    override fun resetCurrentFilePosition() {
        currentPosition = 0
    }

    override fun getAllFilesBefore(): List<String>{
        return files.take(currentPosition)
    }
}