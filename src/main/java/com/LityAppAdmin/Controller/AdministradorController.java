package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.AdministradorModel;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IAdministradorRepository;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import com.LityAppAdmin.Service.GuiaRapidaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/admin")
public class AdministradorController {

    private final GuiaRapidaService guiaRapidaService;

    public AdministradorController(GuiaRapidaService guiaRapidaService) {
        this.guiaRapidaService = guiaRapidaService;
    }



    @Autowired
    private IAdministradorRepository administradorRepository;

    @Autowired
    private IGuiaRapidaRepository guiaRapidaRepository;



    // Muestra el formulario de login
    @GetMapping("/")
    public String showLoginAdmin() {
        return "LoginAdmin"; // Debe devolver el nombre de la vista sin extensión
    }

    // Maneja el login
    @PostMapping("/login")
    public String loginAdmin(@RequestParam("correo") String correo,
                             @RequestParam("password") String password,
                             HttpSession session) {
        System.out.println("Correo recibido: " + correo);
        System.out.println("Contraseña recibida: " + password);

        Optional<AdministradorModel> adminOptional = administradorRepository.findByCorreoAndPassword(correo, password);
        if (adminOptional.isPresent()) {
            session.setAttribute("adminCorreo", correo);
            return "redirect:/api/admin/index";
        } else {
            System.out.println("Administrador no encontrado con el correo y contraseña proporcionados.");
            return "redirect:/api/admin/?error=true";
        }
    }

    // Método privado para obtener la cédula del administrador desde la sesión
    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    // Muestra la página de inicio
    @GetMapping("/index")
    public String mostrarIndexAdmin(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "IndexAdmin"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }


    // Muestra la página para crear guía rápida
    @GetMapping("/crear-guia-rapida")
    public String mostrarCrearGuiaRapida(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "CrearGuiaRapida"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/index"; // Redirige al login si no está autenticado
        }
    }

    @PostMapping("/create-guia")
    public String crearGuia(@RequestParam("tipoDeGuia") String tipoDeGuia,
                            @RequestParam("titulo") String titulo,
                            @RequestParam("parrafo") String parrafo,
                            @RequestParam("imagen") List<MultipartFile> archivos,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            String correo = obtenerCorreoAdministrador(session);
            guiaRapidaService.guardarGuia(tipoDeGuia, titulo, parrafo, archivos, correo);

            // Agregar un mensaje de éxito para mostrarlo en el frontend
            redirectAttributes.addFlashAttribute("successMessage", "¡Guía creada exitosamente!");

            // Redirigir a la misma página para mostrar el mensaje
            return "redirect:/api/admin/crear-guia-rapida";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al crear la guía: " + e.getMessage());
            return "redirect:/api/admin/crear-guia-rapida";
        }
    }

    // Muestra la página para editar guías rápidas
// Muestra la página para editar guías rápidas
    @GetMapping("/editar-guias-rapidas")
    public String mostrarEditarGuiasRapidas(HttpSession session,Model model) {
        if (session.getAttribute("adminCorreo") != null) {
// Obtener la lista de guías de la base de datos
            List<GuiaRapidaModel> guias = guiaRapidaService.obtenerTodasLasGuias(); // Asegúrate de tener este método en el servicio
            model.addAttribute("guias", guias); // Pasar las guías al modelo
            return "EditarGuiasRapidas"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    @DeleteMapping("/eliminar-guia/{id}")
    public String eliminarGuia(@PathVariable("id") Long id) {
        guiaRapidaService.eliminarGuia(id);
        return "redirect:/api/admin/editar-guias-rapidas";
    }


    // Muestra la página para crear nueva experiencia
    @GetMapping("/crear-experiencia")
    public String mostrarCrearExperiencia(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "CrearExperiencia"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    // Muestra la página para editar experiencias
    @GetMapping("/editar-experiencias")
    public String mostrarEditarExperiencias(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "EditarExperiencias"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    // Muestra la página para crear nueva noticia
    @GetMapping("/crear-noticia")
    public String mostrarCrearNoticia(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "CrearNoticia"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    // Muestra la página para editar noticias
    @GetMapping("/editar-noticias")
    public String mostrarEditarNoticias(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "EditarNoticias"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    // Muestra la página para crear nuevo tour
    @GetMapping("/crear-tour")
    public String mostrarCrearTour(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "CrearTour"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    // Muestra la página para editar tours
    @GetMapping("/editar-tours")
    public String mostrarEditarTours(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "EditarTours"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Obtiene la sesión actual
        if (session != null) {
            session.invalidate(); // Invalida la sesión
        }
        return "redirect:/api/admin/"; // Redirige al inicio de sesión
    }


}
