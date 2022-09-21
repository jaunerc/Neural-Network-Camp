import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.io.readCSV

fun readCsv(path: String): List<List<Int>> {
    val df = DataFrame.readCSV(fileOrUrl = path, header = MutableList(785) {"A"}) // use a specific header so that the first line is also proceeded

    return df.rows()
        .map { row -> row.values().stream()
            .map { v -> v as Int}
            .toList() }
        .toList()
}