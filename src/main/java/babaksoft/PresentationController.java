package babaksoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

        // collect undecided groups.
        // heavily inelegant and inefficient code follows...
        // TODO replace with proper repository query instead
        List<ProjectGroup> decidedGroups = new ArrayList<>();

        for (Presentation p: presentations) {
            ProjectGroup g = p.getProjectGroup();
            if (g != null) decidedGroups.add(g);
        }

        List<ProjectGroup> allGroups = new ArrayList<>();

        for (ProjectGroup g : groups) {allGroups.add(g);}

        allGroups.removeAll(decidedGroups);

        model.addAttribute("presentations", presentations);
        model.addAttribute("groups", allGroups);
        return "hello";
    }

    @GetMapping("/assign")
    public String assignProjectGroupToPresentation(@RequestParam int presId, @RequestParam int grId, @RequestParam String studentId, Model model) {
        Presentation pres = presrepo.findOne(presId);
        ProjectGroup pg = pgrepo.findOne(grId);

        if ((pres.getProjectGroup() == null) && (pg.getMembers().contains(studentId))) {
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

    @GetMapping("/list")
    public @ResponseBody String listPicks() {
        String picks = "";
        // should be able to just query the join table, here's an ugly version for now
        Iterable<Presentation> presentations = presrepo.findAll();
        for (Presentation pres : presentations) {
            ProjectGroup pg = pres.getProjectGroup();
            if (pg != null) {
                picks += "pres#" + pres.getTopic() + "#" + pg.getName() + "\n";
            }
        }
        return picks;
    }
}
