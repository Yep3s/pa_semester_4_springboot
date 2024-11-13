package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.AdministradorModel;
import com.LityAppAdmin.Repository.IAdministradorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/api/admin")
public class AdministradorController {


    @Autowired
    private IAdministradorRepository administradorRepository;


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
