import java.nio.file.Files

fun main(args: Array<String>) {
    val neuralNetwork = NeuralNetwork(784, 200, 10, ::sigmoid, 0.1)

    val trainingData = readCsv("src/main/resources/mnist_train_100.csv")
    val firstRecord = trainingData[0]

    val input = firstRecord.stream()
        .skip(1)
        .map { i -> i.toDouble() }
        .toList()

    val target = MutableList(10) { 0.01 }
    target[firstRecord[0]] = 0.99

    neuralNetwork.train(input, target)
}