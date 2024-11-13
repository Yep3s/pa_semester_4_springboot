package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IExperienciaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/admin")
public class ExperienciasAdminController {

    @Autowired
    private IExperienciaRepository experienciasRepository;

    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    @GetMapping("/crear-experiencia")
    public String mostrarCrearExperiencia(HttpSession session,Model model) {
        if (session.getAttribute("adminCorreo") != null) {
            model.addAttribute("experiencia", new ExperienciaModel());
            return "CrearExperiencia"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    @PostMapping("/crear-experiencia")
    public String crearExperiencia(HttpSession session, ExperienciaModel experiencia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "CrearExperiencia";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        experiencia.setCorreoAdministrador(correoAdministrador);
        experienciasRepository.save(experiencia);
        return "redirect:/api/admin/ver-experiencias";

    }

    @GetMapping("/ver-experiencias")
    public String showExperiencias(Model model) {
        model.addAttribute("experiencias", experienciasRepository.findAll());
        return "VerExperiencias";
    }

    @GetMapping("/editar-experiencia/{id}")
    public String mostrarFormularioEdicionGuia(@PathVariable("id") long id, Model model) {
        ExperienciaModel experiencia = experienciasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("experiencia", experiencia);
        return "EditarExperiencia";
    }

    @PostMapping("/actualizar-experiencia/{id}")
    public String updateUser(@PathVariable("id") long id, ExperienciaModel experiencia,
                             BindingResult result, Model model,HttpSession session) {
        if (result.hasErrors()) {
            experiencia.setId(id);
            return "EditarExperiencia";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        experiencia.setCorreoAdministrador(correoAdministrador);

        if (experiencia.getImage() == null || experiencia.getImage().isEmpty()) {
            ExperienciaModel imagenExistente = experienciasRepository.findById(id).orElse(null);
            if (imagenExistente != null) {
                experiencia.setImage(imagenExistente.getImage()); // Mantener la ruta de la imagen anterior
            }
        }

        experienciasRepository.save(experiencia);
        return "redirect:/api/admin/ver-experiencias";
    }

    @GetMapping("/eliminar-experiencia/{id}")
    public String deleteGuia(@PathVariable("id") long id, Model model) {
        ExperienciaModel experiencia = experienciasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        experienciasRepository.delete(experiencia);
        return "redirect:/api/admin/ver-experiencias";
    }




}
