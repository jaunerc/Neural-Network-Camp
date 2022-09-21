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
    return listOf()
}