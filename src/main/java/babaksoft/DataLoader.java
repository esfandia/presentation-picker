package babaksoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PresentationRepository presrepo;

    @Autowired
    private ProjectGroupRepository pgrepo;

    @Value(value = "classpath:static/seed.txt")
    private Resource fileResource;

    public void run(ApplicationArguments args) throws Exception {
        URI f = fileResource.getURI();
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