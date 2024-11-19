package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Repository.IExperienciaRepository;
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
    public String actualizarExperiencia(@PathVariable("id") long id,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                        @ModelAttribute ExperienciaModel experiencia,
                                        BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            experiencia.setId(id);
            return "EditarExperiencia";
        }

        // Obtener el correo del administrador desde la sesión
        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        experiencia.setCorreoAdministrador(correoAdministrador);

        // Verifica si hay un archivo nuevo y si es válido
        if (file != null && !file.isEmpty()) {
            System.out.println("Archivo recibido: " + file.getOriginalFilename()); // Agrega esta línea para ver si el archivo se recibe
            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null &&
                    (originalFileName.endsWith(".png") || originalFileName.endsWith(".jpeg") || originalFileName.endsWith(".jpg"))) {

                // Generar un UUID corto (4 caracteres)
                String shortUUID = UUID.randomUUID().toString().substring(0, 4);

                // Crear el nombre único del archivo
                String uniqueFileName = shortUUID + "_" + originalFileName;
                Path filePath = Paths.get("uploads", uniqueFileName);

                try {
                    // Crear el directorio uploads si no existe
                    Files.createDirectories(filePath.getParent());
                    System.out.println("Ruta de archivo: " + filePath.toString()); // Verifica que la ruta esté correcta

                    // Transferir el archivo
                    file.transferTo(filePath);
                    System.out.println("Archivo guardado exitosamente en: " + filePath.toString()); // Verifica si el archivo se guarda

                    // Actualizar la ruta de la imagen en la base de datos
                    experiencia.setImage("/uploads/" + uniqueFileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error al guardar la imagen", e);
                }
            } else {
                throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se aceptan .png, .jpeg y .jpg");
            }
        } else {
            // Si no hay archivo nuevo, conserva la imagen existente
            ExperienciaModel experienciaExistente = experienciasRepository.findById(id).orElse(null);
            if (experienciaExistente != null) {
                experiencia.setImage(experienciaExistente.getImage());
            }
        }

        // Guardar o actualizar la experiencia en la base de datos
        experienciasRepository.save(experiencia);
        System.out.println("Experiencia guardada: " + experiencia.getId() + ", Imagen: " + experiencia.getImage()); // Verifica si la imagen se actualiza en la base de datos

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
