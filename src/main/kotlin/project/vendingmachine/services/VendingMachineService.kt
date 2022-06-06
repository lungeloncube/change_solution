package project.vendingmachine.services

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

@Service
class VendingMachineService {

    fun calculateDenomination(inputAmount: BigDecimal): String {
        var amount = inputAmount
        val denominations = arrayListOf(50.0, 20.0, 10.0, 5.0, 1.0, 0.2, 0.1)

        //check in input amount from list, if it exists remove it
        if (denominations.contains(amount.toDouble()) && amount.toDouble() != 0.1) {
            denominations.remove(amount.toDouble())
        }

        //create hash map to hold both the denomination and the number of occurrences
        val count: HashMap<Double, Int> = HashMap()

        //iterate denominations and add the denominations and number of occurrences to count hashmap
        for (denomination in denominations) {
            val num = (amount / denomination.toBigDecimal()).toInt()
            count[denomination] = num
            val d = denomination * num
            amount -= d.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        }
        //create empty arraylist to hold the final change
        val res = arrayListOf<String>()

        //iterate through the hashmap items and get hold of the key and value
        for ((k, v) in count) {
            //check and remove denominations that have zero occurrences
            if (v>0) {
                val answer = "$v x ${convertDoubleToCents(k)}"
                res.add(answer)
            }
        }
        return res.joinToString(" + ")
    }

    private fun convertDoubleToCents(value:Double):String{
        return when (value) {
            0.1 -> {
                "10c"
            }
            0.2 -> {
                "20c"
            }
            else -> {
                "R"+value.roundToInt().toString()
            }
        }

    }
}