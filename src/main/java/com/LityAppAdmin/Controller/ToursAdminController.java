package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Model.TourModel;
import com.LityAppAdmin.Repository.ITourRepository;
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
    public String crearTour(HttpSession session,TourModel tour, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "CrearTour";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        tour.setCorreoAdministrador(correoAdministrador);
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
    public String updateUser(@PathVariable("id") long id, TourModel tour,
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

        if (tour.getImage() == null || tour.getImage().isEmpty()) {
            TourModel imagenExistente = tourRepository.findById(id).orElse(null);
            if (imagenExistente != null) {
                tour.setImage(imagenExistente.getImage()); // Mantener la ruta de la imagen anterior
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
