package project.vendingmachine.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.vendingmachine.models.ChangeRequest
import project.vendingmachine.models.ChangeResponse
import project.vendingmachine.services.VendingMachineService
import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

@RestController
@RequestMapping()
class VendingMachineController {

    @Autowired
    private lateinit var service: VendingMachineService

    @PostMapping("change/")
    fun calculateChange(@RequestBody changeRequest: ChangeRequest): ChangeResponse {
        var amount = changeRequest.amount.setScale(2)

        // If input is less than least denomination, return as is
        if(amount >= BigDecimal.ZERO && amount < BigDecimal(0.1)){
            return ChangeResponse(change = amount.toString())
        }
        if(amount < BigDecimal.ZERO ){
            return ChangeResponse("input amount can not be negative")
        }

        val result = service.calculateDenomination(amount)

        return ChangeResponse(change = result)
    }


}