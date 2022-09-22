fun main() {
    val trainingFilePath = "src/main/resources/mnist_train.csv"
    val testDataPath = "src/main/resources/mnist_test.csv"

    val neuralNetwork = trainNeuralNetwork(trainingFilePath, 0.1, 5)

    measureScore(neuralNetwork, testDataPath)
}
