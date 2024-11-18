package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/lity")
public class UGuiasRapidasController {

    @Autowired
    private IGuiaRapidaRepository guiaRapidaRepository;

    @GetMapping("/guias-rapidas")
    public String mostrarCategoriasGuiasRapidas() {
        return "GuiasRapidasUsuario";
    }

    @GetMapping("/guias")
    public String mostrarGuiasRapidas(@RequestParam("tipoDeGuia") String tipoDeGuia, Model model) {

        List<GuiaRapidaModel> guias = guiaRapidaRepository.findBytipoDeGuia(tipoDeGuia);

        model.addAttribute("guiasRapidas", guias);
        model.addAttribute("tipoDeGuia", tipoDeGuia);


        return "GuiasRapidasUsuario"; // Nombre del archivo HTML sin la extensión
    }





}
