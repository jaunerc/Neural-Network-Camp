import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.math.exp
import org.jetbrains.kotlinx.multik.api.ones
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.operations.div
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.jetbrains.kotlinx.multik.ndarray.operations.unaryMinus

fun sigmoid(x: D1Array<Double>): D1Array<Double> {
    val onesVector = Multik.ones<Double>(x.size)

    return onesVector / (Multik.math.exp(-x) + onesVector)
}


fun sigmoid2(x: D2Array<Double>): D2Array<Double> {
    val onesVector = Multik.ones<Double>(x.size, 1)

    return onesVector / (Multik.math.exp(-x) + onesVector)
}
