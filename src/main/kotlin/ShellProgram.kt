private const val TRAINING_FILE_PATH = "src/main/resources/mnist_train.csv"
private const val TEST_FILE_PATH = "src/main/resources/mnist_test.csv"

class ShellProgram {
    private var neuralNetwork: NeuralNetwork? = null

    fun start() {
        println("Mnist Neural Network Program")
        inputReader()
    }

    private fun inputReader() {
        while (true) {
            printCommandInfo()

            val input = readln()

            if (input == "t") {
                executeTrainCommand()
            } else if (input == "m") {
                executeMeasureCommand()
            }
        }
    }

    private fun executeMeasureCommand() {
        println("measure the neural network")
        neuralNetwork?.let { measureScore(it, TEST_FILE_PATH) }
    }

    private fun executeTrainCommand() {
        println("learning rate (between 0.1 and 0.9):")
        val learningRate = readln().toDouble()

        println("epochs:")
        val epochs = readln().toInt()

        println("start with training...")
        neuralNetwork = trainNeuralNetwork(TRAINING_FILE_PATH, learningRate, epochs)

        println("training is done")
    }

    private fun printCommandInfo() {
        println("-------------------------------")
        println("type 't' to train a new neural network")
        println("type 'm' to measure the neural network")
    }
}