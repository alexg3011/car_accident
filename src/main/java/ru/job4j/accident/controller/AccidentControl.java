package ru.job4j.accident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.service.AccidentService;

@Controller
public class AccidentControl {
    private final AccidentService accidents;

    @Autowired
    public AccidentControl(AccidentService accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        accidents.addType(AccidentType.of(1, "Две машины"));
        accidents.addType(AccidentType.of(2, "Машина и человек"));
        accidents.addType(AccidentType.of(3, "Машина и велосипед"));
        model.addAttribute("types", accidents.getAllTypes());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidents.addAccident(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        Accident accident = accidents.getAccident(id);
        model.addAttribute("accident", accident);
        return "accident/update";
    }

    @PostMapping("/update")
    public String update(@RequestParam("name") String name, @RequestParam("id") int id) {
        Accident editAcc = accidents.getAccident(id);
        editAcc.setName(name);
        accidents.updateAccident(editAcc);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        accidents.deleteAccident(id);
        return "redirect:/";
    }
}