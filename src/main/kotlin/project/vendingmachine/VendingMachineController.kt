package project.vendingmachine

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.vendingmachine.models.ChangeRequest
import project.vendingmachine.models.ChangeResponse
import kotlin.math.roundToInt

@RestController
@RequestMapping()
class VendingMachineController {

    @PostMapping("change/")
    fun calculateChange(@RequestBody changeRequest: ChangeRequest): ChangeResponse {
        var amount = changeRequest.amount
        if(amount<0.1){
            return ChangeResponse(change = amount.toString())
        }

        val denominations = arrayListOf<Double>(50.0, 20.0, 10.0, 5.0, 1.0, 0.2, 0.1)

        //check in input amount from list, if it exists remove it
        if (denominations.contains(amount) && amount != 0.1) {
            denominations.remove(amount)
        }

        //create hash map to hold both the denomination and the number of occurrences
        var count: HashMap<Double, Int> = HashMap()

        //iterate denominations and add the denominations and number of occurrences to count hashmap
        for (denomination in denominations) {
            var num = (amount / denomination).toInt()
            count[denomination] = num
           var d =denomination * num
            amount -= d
        }
        //create empty arraylist to hold the final change
        var res = arrayListOf<String>()

        //iterate through the hashmap items and get hold of the key and value
        for ((k, v) in count) {

            //check and remove denominations that have zero occurrences
            if (v>0) {
                val answer = "R${k} * $v"
                res.add(answer)

            }
        }
        return ChangeResponse(change = res.joinToString(" + "))

    }
}