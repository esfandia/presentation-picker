package babaksoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Scanner;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PresentationRepository presrepo;

    @Autowired
    private ProjectGroupRepository pgrepo;

    //@Value("#{seed ?: \"classpath:static/seed.txt\"}")
    @Value("${seed}")
    private Resource seed;

    public void run(ApplicationArguments args) throws Exception {
        if (seed == null) seed = new UrlResource("classpath:static/seed.txt");
        URI f = seed.getURI();
        Scanner s = new Scanner(f.toURL().openStream());
        String input;
        while (s.hasNextLine()) {
            input = s.nextLine();
            String [] splitinput = input.split("#");
            switch (splitinput[0]) {
                case "group":
                    ProjectGroup pg = new ProjectGroup((splitinput[1]));
                    for (int i = 2; i < splitinput.length; i++) {
                        pg.addMember(splitinput[i]);
                    }
                    pgrepo.save(pg);

                    break;
                case "pres":
                    presrepo.save(new Presentation((splitinput[1])));
                    break;
            }
        }
    }
}