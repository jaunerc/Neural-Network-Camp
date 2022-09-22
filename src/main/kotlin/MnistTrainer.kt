import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.rand
import org.jetbrains.kotlinx.multik.ndarray.operations.minus

private const val numInputNodes = 784
private const val numHiddenNodes = 200
private const val numOutputNodes = 10

fun trainNeuralNetwork(trainingFilePath: String, learningRate: Double, epochs: Int): NeuralNetwork {
    val weightsInputHidden = Multik.rand<Double>(numHiddenNodes, numInputNodes) - 0.5
    val weightsHiddenOutput = Multik.rand<Double>(numOutputNodes, numHiddenNodes) - 0.5
    val neuralNetwork = NeuralNetwork(numInputNodes, numHiddenNodes, numOutputNodes, ::sigmoid, learningRate, weightsInputHidden, weightsHiddenOutput)
    val trainingData = readCsv(trainingFilePath)

    trainNetwork(neuralNetwork, trainingData, epochs)

    return neuralNetwork
}

fun trainNetwork(neuralNetwork: NeuralNetwork, trainingData: List<List<Int>>, epochs: Int) {
    for (i in 1..epochs) {
        trainingData.stream()
            .forEach { record -> proceedRecord(neuralNetwork, record) }
        println("epoch $i done")
    }
}

private fun proceedRecord(neuralNetwork: NeuralNetwork, record: List<Int>) {
    val input = record.stream()
        .skip(1)
        .map(::normalizeRecord)
        .toList()

    val target = createTargetRecord(record[0])

    neuralNetwork.train(input, target)
}

private fun createTargetRecord(correctNumberIndex: Int): MutableList<Double> {
    // the target record has a high value of almost 1 at the correct number index and all other values are almost 0
    val target = MutableList(numOutputNodes) { 0.01 }
    target[correctNumberIndex] = 0.99
    return target
}

fun measureScore(neuralNetwork: NeuralNetwork, testDataPath: String) {
    val testData = readCsv(testDataPath)
    val correctAnswers = testData.stream()
        .map { record -> queryRecord(neuralNetwork, record) }
        .filter { result -> result == true }
        .count()

    val score = correctAnswers / testData.size.toDouble()

    println("score: $score")
}

fun queryRecord(neuralNetwork: NeuralNetwork, record: List<Int>): Boolean {
    val input = record.stream()
        .skip(1)
        .map(::normalizeRecord)
        .toList()

    val result = neuralNetwork.query(input)

    val indexWithHighestScore = result.data.withIndex().maxByOrNull { it.value }?.index

    return record[0] == indexWithHighestScore
}

private fun normalizeRecord(grayValue: Int) = grayValue.toDouble() / 255.0 * 0.99 + 0.01