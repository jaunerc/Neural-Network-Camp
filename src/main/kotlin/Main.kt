fun main(args: Array<String>) {
    val neuralNetwork = NeuralNetwork(784, 200, 10, ::sigmoid2, 0.1)

    val trainingData = readCsv("src/main/resources/mnist_train.csv")

    trainNetwork(neuralNetwork, trainingData)

    val testData = readCsv("src/main/resources/mnist_test.csv")

    measureScore(neuralNetwork, testData)
}

fun trainNetwork(neuralNetwork: NeuralNetwork, trainingData: List<List<Int>>) {
    for (i in 1..5) {
        trainingData.stream()
            .forEach { record -> proceedRecord(neuralNetwork, record) }
        println("epoch $i done")
    }
}

fun proceedRecord(neuralNetwork: NeuralNetwork, record: List<Int>) {
    val input = record.stream()
        .skip(1)
        .map { i -> i.toDouble() / 255.0 * 0.99 + 0.01 }
        .toList()

    val target = MutableList(10) { 0.01 }
    target[record[0]] = 0.99

    neuralNetwork.train(input, target)
}

fun measureScore(neuralNetwork: NeuralNetwork, testData: List<List<Int>>) {
    val correctAnswers = testData.stream()
        .map { record -> queryRecord(neuralNetwork, record) }
        .filter { result -> result == true }
        .count()

    val score = correctAnswers / testData.size.toDouble()

    println("Correct answers: $score")
}

fun queryRecord(neuralNetwork: NeuralNetwork, record: List<Int>): Boolean {
    val input = record.stream()
        .skip(1)
        .map { i -> i.toDouble() / 255.0 * 0.99 + 0.01 }
        .toList()

    val result = neuralNetwork.query(input)

    val indexWithHighestScore = result.data.withIndex().maxByOrNull { it.value }?.index

    return record[0] == indexWithHighestScore
}