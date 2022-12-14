import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.plusAssign
import org.jetbrains.kotlinx.multik.ndarray.operations.times

class NeuralNetwork(
    val inputNodes: Int,
    val hiddenNodes: Int,
    val outputNodes: Int,
    private val activation: (D2Array<Double>) -> D2Array<Double>,
    val learningRate: Double,
    val weightsInputHidden: D2Array<Double>,
    val weightsHiddenOutput: D2Array<Double>
) {
    fun train(inputs: List<Double>, targets: List<Double>) {
        val inputsNd = Multik.ndarray(inputs, 784, 1)
        val targetsNd = Multik.ndarray(targets, 10, 1)

        val hiddenInputs = Multik.linalg.dot(weightsInputHidden, inputsNd)
        val hiddenOutputs = activation(hiddenInputs)

        val finalInputs = Multik.linalg.dot(weightsHiddenOutput, hiddenOutputs)
        val finalOutputs = activation(finalInputs)

        val outputErrors = targetsNd - finalOutputs
        val hiddenErrors = Multik.linalg.dot(weightsHiddenOutput.transpose(), outputErrors)

        weightsHiddenOutput += learningRate * Multik.linalg.dot(
            (outputErrors * finalOutputs * (1.0 - finalOutputs)),
            hiddenOutputs.transpose()
        )

        weightsInputHidden += learningRate * Multik.linalg.dot(
            (hiddenErrors * hiddenOutputs * (1.0 - hiddenOutputs)),
            inputsNd.transpose()
        )
    }

    fun query(inputs: List<Double>): D2Array<Double> {
        val inputsNd = Multik.ndarray(inputs, 784, 1)

        val hiddenInputs = Multik.linalg.dot(weightsInputHidden, inputsNd)
        val hiddenOutputs = activation(hiddenInputs)

        val finalInputs = Multik.linalg.dot(weightsHiddenOutput, hiddenOutputs)
        return activation(finalInputs)
    }
}