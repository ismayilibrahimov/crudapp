package az.touragency.controllers;

import az.touragency.models.Tour;
import az.touragency.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @Autowired
    private TourRepository tourRepository;

    @GetMapping
    public String home(Model model)
    {
        List<Tour> tours = tourRepository.findAll();
        model.addAttribute("tours", tours);
        return "admin/home";
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("tour", new Tour());
        return "admin/create";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Tour tour)
    {
        tourRepository.save(tour);
        return "redirect:/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        Optional<Tour> tour = tourRepository.findById(id);
        model.addAttribute("tour", tour.get());
        return "admin/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Tour tour) {
        tourRepository.save(tour);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        tourRepository.delete(tourRepository.findById(id).get());
        return "redirect:/dashboard";
    }
}
