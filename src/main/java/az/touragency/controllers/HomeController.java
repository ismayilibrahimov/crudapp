package az.touragency.controllers;

import az.touragency.models.Tour;
import az.touragency.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TourRepository tourRepository;


    @GetMapping("/")
    public String home(Model model)
    {
        List<Tour> tours = tourRepository.findAll();
        model.addAttribute("tours", tours);
        return "home";
    }

    @GetMapping("/content/{id}")
    public String content(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("tour", tourRepository.findById(id).get());
        return "content";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }




    @GetMapping("/about")
    public String about()
    {
        return "about";
    }

    @GetMapping("/contact")
    public String contact()
    {
        return "contact";
    }
}
