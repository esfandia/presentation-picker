package babaksoft;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PresentationPickerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private PresentationRepository repository;

    @Test
    public void correctlyInsertsPresentation() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/tyty",
                String.class)).contains("tyty");
    }

    @Test
    public void correctlyIncludesPresentationAfterLocalInsertion() {
        Presentation pres = new Presentation("fafa");
        repository.save(pres);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port,
                String.class)).contains("fafa");
    }
}
