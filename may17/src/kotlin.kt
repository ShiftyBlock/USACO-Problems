import java.io.IOException
import java.util.*

object kotlin {
    fun main() {
        val sc = Scanner(System.`in`)
        var tcs = sc.nextInt()
        while (tcs-- > 0) {
            val a = sc.nextInt()
            val b = sc.nextInt()
            val minus = Math.min(a, b) - 1
            println(1.toString() + " " + minus + " " + (Math.max(a, b) - minus))
        }
    }
}