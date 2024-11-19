package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Model.TourModel;
import com.LityAppAdmin.Service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/lity")
public class UToursController {

    @Autowired
    private TourService tourService;

    @GetMapping("/toursUser")
    public String mostrarTours(Model model) {

        List<TourModel> tours = tourService.obtenerToursRecientes();
        model.addAttribute("tours", tours);
        return "ToursUsuario";
    }





}
