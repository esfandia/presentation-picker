package babaksoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PresentationController {

    @Autowired
    private PresentationRepository presrepo;

    @Autowired
    private ProjectGroupRepository pgrepo;

    @GetMapping
    public String displayAll(Model model) {
        Iterable<Presentation> presentations = presrepo.findAll();
        Iterable<ProjectGroup> groups = pgrepo.findAll();
        model.addAttribute("presentations", presentations);
        model.addAttribute("groups", groups);
        return "Hello";
    }

    @GetMapping("/assign")
    public String assignProjectGroupToPresentation(@RequestParam int presId, @RequestParam int grId, Model model) {
        Presentation pres = presrepo.findOne(presId);
        ProjectGroup pg = pgrepo.findOne(grId);
        if (pres.getProjectGroup() == null) {
            pres.setProjectGroup(pg);
            presrepo.save(pres);
        }
        return "redirect:/";
    }

    @GetMapping("/{name}")
    public String stupidTopicAdding(@PathVariable String name, Model model) {
        Presentation pres = new Presentation(name);
        ProjectGroup g = new ProjectGroup("default");
        pres.setProjectGroup(g);
        presrepo.save(pres);
        Presentation pres2 = new Presentation(name + " without proj");
        presrepo.save(pres2);
        return "redirect:/";
    }
}
