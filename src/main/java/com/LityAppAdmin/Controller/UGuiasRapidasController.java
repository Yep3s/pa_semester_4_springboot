package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import com.LityAppAdmin.Service.GuiaRapidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/api/lity")
public class UGuiasRapidasController {

    @Autowired
    private IGuiaRapidaRepository guiaRapidaRepository;

    @GetMapping("/guiasRapidasUser")
    public String mostrarGuiasRapidas() {
        return "GuiasRapidasUsuario";
    }

    @GetMapping("/guiasRapidasAtracciones")
    public String mostrarGuiasRapidasAtracciones(Model model) {

        List<GuiaRapidaModel> guiasAtracciones = guiaRapidaRepository.findByTipoDeGuia("Atracciones");
        model.addAttribute("guias", guiasAtracciones);
        return "GuiasRapidasAtracciones";

    }

    @GetMapping("/guiasRapidasComidaTipica")
    public String mostrarGuiasRapidasComidaTipica(Model model) {

        List<GuiaRapidaModel> guiasComida = guiaRapidaRepository.findByTipoDeGuia("Comida Típica");
        model.addAttribute("guias", guiasComida);

        return "GuiasRapidasComidaTipica";
    }

    @GetMapping("/guiasRapidasTransporte")
    public String mostrarGuiasRapidasTransporte(Model model) {

        List<GuiaRapidaModel> guiasTransporte = guiaRapidaRepository.findByTipoDeGuia("Transporte");
        model.addAttribute("guias", guiasTransporte);

        return "GuiasRapidasTransporte";
    }

    @GetMapping("/tipsDeSeguridad")
    public String mostrarTipsDeSeguridad(Model model) {

        List<GuiaRapidaModel> tipsSeguridad = guiaRapidaRepository.findByTipoDeGuia("Seguridad");
        model.addAttribute("guias", tipsSeguridad);

        return "TipsDeSeguridad";
    }








}
