package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Model.TourModel;
import com.LityAppAdmin.Repository.ITourRepository;
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
public class ToursAdminController {

    @Autowired
    private ITourRepository tourRepository;

    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    @GetMapping("/crear-tour")
    public String mostrarCrearTour(HttpSession session,Model model) {
        if (session.getAttribute("adminCorreo") != null) {
            model.addAttribute("tour", new TourModel());
            return "CrearTour"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }


    @PostMapping("/crear-tour")
    public String crearTour(@RequestParam("file") MultipartFile file, @ModelAttribute TourModel tour ,HttpSession session) {

        // Obtener correo del administrador desde la sesión
        String correoAdmin = obtenerCorreoAdministrador(session);
        tour.setCorreoAdministrador(correoAdmin);

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
                    tour.setImage("/uploads/" + uniqueFileName);
                } catch (IOException e) {
                    throw new RuntimeException("Error al guardar la imagen", e);
                }
            }else {
                throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se aceptan .png, .jpeg y .jpg");
            }
        }

        tourRepository.save(tour);
        return "redirect:/api/admin/ver-tours";

    }

    @GetMapping("/ver-tours")
    public String showTours(Model model) {
        model.addAttribute("tours", tourRepository.findAll());
        return "VerTours";
    }

    @GetMapping("/editar-tour/{id}")
    public String mostrarFormularioEdicionGuia(@PathVariable("id") long id, Model model) {
        TourModel tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("tour", tour);
        return "EditarTour";
    }

    @PostMapping("/actualizar-tour/{id}")
    public String updateUser(@PathVariable("id") long id,@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute TourModel tour,
                             BindingResult result, Model model,HttpSession session) {
        if (result.hasErrors()) {
            tour.setId(id);
            return "EditarTour";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        tour.setCorreoAdministrador(correoAdministrador);

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
                    tour.setImage("/uploads/" + uniqueFileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error al guardar la imagen", e);
                }
            } else {
                throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se aceptan .png, .jpeg y .jpg");
            }
        } else {
            // Si no hay archivo nuevo, conserva la imagen existente
            TourModel tourExistente = tourRepository.findById(id).orElse(null);
            if (tourExistente != null) {
                tour.setImage(tourExistente.getImage());
            }
        }



        tourRepository.save(tour);
        return "redirect:/api/admin/ver-tours";
    }


    @GetMapping("/eliminar-tour/{id}")
    public String deleteGuia(@PathVariable("id") long id, Model model) {
        TourModel tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        tourRepository.delete(tour);
        return "redirect:/api/admin/ver-tours";
    }



}
