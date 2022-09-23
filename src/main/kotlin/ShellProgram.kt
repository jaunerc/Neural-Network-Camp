private const val TRAINING_FILE_PATH = "src/main/resources/mnist_train.csv"
private const val TEST_FILE_PATH = "src/main/resources/mnist_test.csv"

class ShellProgram {
    private var neuralNetwork: NeuralNetwork? = null

    fun start() {
        println("Mnist Neural Network Program")
        inputReader()
    }

    private fun printCommandInfo() {
        println("-------------------------------")
        println("type 't' to train a new neural network")
        println("type 'm' to measure the neural network")
        println("type 's' to save the neural network")
        println("type 'l' to load a neural network from a file")
        println("type 'q' to query the neural network with an image of a number")
    }

    private fun inputReader() {
        while (true) {
            printCommandInfo()

            when (readln()) {
                "t" -> {
                    executeTrainCommand()
                }
                "m" -> {
                    executeMeasureCommand()
                }
                "s" -> {
                    executeSaveCommand()
                }
                "l" -> {
                    executeLoadCommand()
                }
                "q" -> {
                    executeQueryCommand()
                }
            }
        }
    }

    private fun executeQueryCommand() {
        println("file path to the number image file:")
        val filePath = readln()

        val pngVector = readPng(filePath).stream()
            .map(::normalizeRecord)
            .toList()

        val resultVector = neuralNetwork?.query(pngVector)
        val networkGuess = resultVector?.data?.withIndex()?.maxByOrNull { it.value }?.index

        println(resultVector)
        println()
        println("the network says")
        println(networkGuess)
    }

    private fun executeLoadCommand() {
        println("file path to the json file:")
        val filePath = readln()

        val json = readNeuralNetwork(filePath)
        neuralNetwork = convertFrom(json)
    }

    private fun executeSaveCommand() {
        if (neuralNetwork == null) {
            println("ERROR: there is no neural network present")
            println("please train a new neural network or import an existing one")
        } else {
            println("file path:")
            val filePath = readln()

            val json = convertTo(neuralNetwork!!)
            saveNeuralNetwork(json, filePath)
        }
    }

    private fun executeMeasureCommand() {
        if (neuralNetwork == null) {
            println("ERROR: there is no neural network present")
            println("please train a new neural network or import an existing one")
        } else {
            println("measure the neural network")
            measureScore(neuralNetwork!!, TEST_FILE_PATH)
        }
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
}