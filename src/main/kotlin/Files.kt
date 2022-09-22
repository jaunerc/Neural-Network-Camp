import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.io.readCSV
import java.io.File
import javax.imageio.ImageIO

fun readCsv(path: String): List<List<Int>> {
    val df = DataFrame.readCSV(fileOrUrl = path, header = MutableList(785) {"A"}) // use a specific header so that the first line is also proceeded

    return df.rows()
        .map { row -> row.values().stream()
            .map { v -> v as Int}
            .toList() }
        .toList()
}

fun readPng(path: String): List<Int> {
    val img = ImageIO.read(File(path))
    val grayValues = mutableListOf(3)

    for (x in 0 until img.width) {
        for (y in 0 until img.height) {
            grayValues.add(img.data.getSample(x, y, 0))
        }
    }

    return grayValues
}

fun saveNeuralNetwork(json: String, filePath: String) {
    File(filePath).writeText(json)
}

fun readNeuralNetwork(filePath: String): String {
    return File(filePath).readText()
}