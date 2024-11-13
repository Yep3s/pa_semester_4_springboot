package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/api/admin")
public class GuiaRapidaAdminController {

    @Autowired
    private IGuiaRapidaRepository guiaRapidaRepository;


    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    @GetMapping("/crear-guia-rapida")
    public String mostrarCrearGuiaRapida(HttpSession session,Model model) {
        if (session.getAttribute("adminCorreo") != null) {
            model.addAttribute("guia", new GuiaRapidaModel());
            return "CrearGuiaRapida"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }


    @PostMapping("/crear-guia-rapida")
    public String crearGuiaRapida(HttpSession session,GuiaRapidaModel guia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "CrearGuiaRapida";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        guia.setCorreoAdministrador(correoAdministrador);
        guiaRapidaRepository.save(guia);
        return "redirect:/api/admin/ver-guias-rapidas";

    }

    @GetMapping("/ver-guias-rapidas")
    public String showGuiasRapidas(Model model) {
        model.addAttribute("guiasRapidas", guiaRapidaRepository.findAll());
        return "VerGuiasRapidas";
    }

    @GetMapping("/editar-guia-rapida/{id}")
    public String mostrarFormularioEdicionGuia(@PathVariable("id") long id, Model model) {
        GuiaRapidaModel guia = guiaRapidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("guia", guia);
        return "EditarGuiaRapida";
    }

    @PostMapping("/actualizar-guia-rapida/{id}")
    public String updateUser(@PathVariable("id") long id, GuiaRapidaModel guia,
                             BindingResult result, Model model,HttpSession session) {
        if (result.hasErrors()) {
            guia.setId(id);
            return "EditarGuiaRapida";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        guia.setCorreoAdministrador(correoAdministrador);

        if (guia.getImage() == null || guia.getImage().isEmpty()) {
            GuiaRapidaModel imagenExistente = guiaRapidaRepository.findById(id).orElse(null);
            if (imagenExistente != null) {
                guia.setImage(imagenExistente.getImage()); // Mantener la ruta de la imagen anterior
            }
        }

        guiaRapidaRepository.save(guia);
        return "redirect:/api/admin/ver-guias-rapidas";
    }


    @GetMapping("/eliminar-guia-rapida/{id}")
    public String deleteGuia(@PathVariable("id") long id, Model model) {
        GuiaRapidaModel guia = guiaRapidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        guiaRapidaRepository.delete(guia);
        return "redirect:/api/admin/ver-guias-rapidas";
    }








}
