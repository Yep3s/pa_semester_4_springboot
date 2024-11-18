package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Repository.IExperienciaRepository;
import com.LityAppAdmin.Service.ExperienciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/lity")
public class UExperienciasController {

    @Autowired
    private ExperienciaService experienciaService;

    @GetMapping("/experienciasUser")
    public String mostrarExperiencias(Model model) {

        List<ExperienciaModel> experiencias = experienciaService.obtenerExperienciasRecientes();
        model.addAttribute("experiencias", experiencias);
        return "ExperienciasUsuario";
    }


}
