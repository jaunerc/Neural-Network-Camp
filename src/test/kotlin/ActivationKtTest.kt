import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.ndarray
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.pow

internal class ActivationKtTest {

    private val sigmoidValue = 1.0 / (1.0 + Math.E.pow(-1.0))

    @Test
    fun sigmoid_threeVector() {
        val x = Multik.ndarray(listOf(1.0, 1.0, 1.0))
        val expected = Multik.ndarray(listOf(sigmoidValue, sigmoidValue, sigmoidValue))

        val result = sigmoid(x)

        assertEquals(expected, result)
    }

    @Test
    fun sigmoid_fiveVector() {
        val x = Multik.ndarray(listOf(1.0, 1.0, 1.0, 1.0, 1.0))
        val expected = Multik.ndarray(listOf(sigmoidValue, sigmoidValue, sigmoidValue, sigmoidValue, sigmoidValue))

        val result = sigmoid(x)

        assertEquals(expected, result)
    }
}