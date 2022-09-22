import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.operations.toListD2

fun convertTo(neuralNetwork: NeuralNetwork): String {
    val neuralNetworkData = NeuralNetworkJson(
        neuralNetwork.inputNodes,
        neuralNetwork.hiddenNodes,
        neuralNetwork.outputNodes,
        neuralNetwork.learningRate,
        neuralNetwork.weightsInputHidden.toListD2(),
        neuralNetwork.weightsHiddenOutput.toListD2()
    )

    return Json.encodeToString(neuralNetworkData)
}

fun convertFrom(json: String): NeuralNetwork {
    val neuralNetworkData = Json.decodeFromString<NeuralNetworkJson>(json)

    return NeuralNetwork(
        neuralNetworkData.inputNodes,
        neuralNetworkData.hiddenNodes,
        neuralNetworkData.outputNodes,
        ::sigmoid,
        neuralNetworkData.learningRate,
        Multik.ndarray(neuralNetworkData.weightsInputHidden),
        Multik.ndarray(neuralNetworkData.weightsHiddenOutput)
    )
}