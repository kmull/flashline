package pl.flashline;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"flashline.jwt.secret=test-secret-tylko-do-testow-min-32-znaki-dlugosci"
})
class FlashlineApplicationTests {

	@Test
	void contextLoads() {
	}

}
