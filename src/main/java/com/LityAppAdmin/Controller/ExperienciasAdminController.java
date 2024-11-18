package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IExperienciaRepository;
import com.LityAppAdmin.Service.ExperienciaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


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
    public String crearExperiencia(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute ExperienciaModel experiencia,
            HttpSession session) {

        // Obtener correo del administrador desde la sesión
        String correoAdmin = obtenerCorreoAdministrador(session);
        experiencia.setCorreoAdministrador(correoAdmin);

        // Validar si el archivo tiene una extensión permitida
        if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null &&
                    (originalFileName.endsWith(".png") || originalFileName.endsWith(".jpeg") || originalFileName.endsWith(".jpg"))) {

                // Generar un UUID corto (4 caracteres)
                String shortUUID = UUID.randomUUID().toString().substring(0, 4);

                // Crear el nombre único del archivo
                String uniqueFileName = shortUUID + "_" + originalFileName;
                Path filePath = Paths.get("uploads", uniqueFileName);

                try {
                    Files.createDirectories(filePath.getParent());
                    file.transferTo(filePath);
                    experiencia.setImage("/uploads/" + uniqueFileName);
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar la imagen", e);
                }
            } else {
                throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se aceptan .png, .jpeg y .jpg");
            }
        }

        // Guardar experiencia en la base de datos
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
