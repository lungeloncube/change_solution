package project.vendingmachine

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
@AutoConfigureMockMvc
class VendingMachineApplicationTests (@Autowired val mockMvc: MockMvc,
									  @Autowired val objectMapper: ObjectMapper
){

	@Test
	fun contextLoads() {
	}



}
