
@kotlinx.serialization.Serializable
class NeuralNetworkJson(
    val inputNodes: Int,
    val hiddenNodes: Int,
    val outputNodes: Int,
    val learningRate: Double,
    val weightsInputHidden: List<List<Double>>,
    val weightsHiddenOutput: List<List<Double>>
) {
}