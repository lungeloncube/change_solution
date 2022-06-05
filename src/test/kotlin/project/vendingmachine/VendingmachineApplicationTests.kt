package project.vendingmachine

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI


@ExtendWith(SpringExtension::class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	classes = [VendingMachineApplicationTests.ControllerTestConfig::class],
	properties = ["spring.example.property=foobar"]
)
@ActiveProfiles(value = ["test"])
class VendingMachineApplicationTests {
	var testRestTemplate = TestRestTemplate()

	@LocalServerPort
	var serverPort: Int = 0

	@TestConfiguration
	internal class ControllerTestConfig {

	}

	private fun applicationUrl() = "http://localhost:9000/change/"

	@Test
	fun testGetChangeFrom100() {
		val url = applicationUrl()
		val requestJson = "{\"amount\":100}"
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val entity = HttpEntity(requestJson, headers)
		val result = testRestTemplate.postForEntity(url, entity, String::class.java)
		Assertions.assertEquals(HttpStatus.OK, result.statusCode)
		Assertions.assertEquals("{\"change\":\"R50.0 * 2\"}", result.body)
	}

	@Test
	fun testGetChangeFrom50() {
		val url = applicationUrl()
		val requestJson = "{\"amount\":50}"
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val entity = HttpEntity(requestJson, headers)
		val result = testRestTemplate.postForEntity(url, entity, String::class.java)
		Assertions.assertEquals(HttpStatus.OK, result.statusCode)
		Assertions.assertEquals("{\"change\":\"R20.0 * 2 + R10.0 * 1\"}", result.body)
	}

	@Test
	fun testGetChangeFrom30() {
		val url = applicationUrl()
		val requestJson = "{\"amount\":30}"
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val entity = HttpEntity(requestJson, headers)
		val result = testRestTemplate.postForEntity(url, entity, String::class.java)
		Assertions.assertEquals(HttpStatus.OK, result.statusCode)
		Assertions.assertEquals("{\"change\":\"R20.0 * 1 + R10.0 * 1\"}", result.body)
	}


}
