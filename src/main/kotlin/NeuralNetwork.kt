import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.rand
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.plusAssign
import org.jetbrains.kotlinx.multik.ndarray.operations.times

class NeuralNetwork(
    inputNodes: Int,
    hiddenNodes: Int,
    outputNodes: Int,
    private val activation: (D1Array<Double>) -> D1Array<Double>,
    private val learningRate: Double
) {
    private val weightsInputHidden = Multik.rand<Double>(hiddenNodes, inputNodes) - 0.5
    private val weightsHiddenOutput = Multik.rand<Double>(outputNodes, hiddenNodes) - 0.5

    fun train(inputs: List<Double>, targets: List<Double>) {
        val inputsNd = Multik.ndarray(inputs)
        val targetsNd = Multik.ndarray(targets)

        val hiddenInputs = Multik.linalg.dot(weightsInputHidden, inputsNd)
        val hiddenOutputs = activation(hiddenInputs)

        val finalInputs = Multik.linalg.dot(weightsHiddenOutput, hiddenOutputs)
        val finalOutputs = activation(finalInputs)

        val outputErrors = targetsNd - finalOutputs
        val hiddenErrors = Multik.linalg.dot(weightsHiddenOutput.transpose(), outputErrors)

        weightsHiddenOutput += learningRate * Multik.linalg.dot(
            (outputErrors * finalOutputs * (1.0 - finalOutputs)),
            hiddenOutputs
        )

        weightsInputHidden += learningRate * Multik.linalg.dot(
            (hiddenErrors * hiddenOutputs * (1.0 - hiddenOutputs)),
            inputsNd
        )
    }

    fun query(inputs: List<Double>): D1Array<Double> {
        val inputsNd = Multik.ndarray(inputs)

        val hiddenInputs = Multik.linalg.dot(weightsInputHidden, inputsNd)
        val hiddenOutputs = activation(hiddenInputs)

        val finalInputs = Multik.linalg.dot(weightsHiddenOutput, hiddenOutputs)
        return activation(finalInputs)
    }
}