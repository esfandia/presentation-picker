package babaksoft;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PresentationPickerMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PresentationRepository repository;

    @Test
    public void correctlyIncludesPresentationAfterLocalInsertion() throws Exception {
        Presentation pres = new Presentation("fefe");
        repository.save(pres);

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("fefe")));
    }

    @Test
    public void correctlyIncludesPresentationAndItsGroupAfterLocalInsertion() throws Exception {
        Presentation pres = new Presentation("fefe");
        ProjectGroup pg = new ProjectGroup("beuarh");
        pres.setProjectGroup(pg);
        repository.save(pres);

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("fefe")))
                .andExpect(content().string(containsString("beuarh")));
    }
}
