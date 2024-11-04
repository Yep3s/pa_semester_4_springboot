package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.AdministradorModel;
import com.LityAppAdmin.Repository.IAdministradorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            session.setAttribute("admin", adminOptional.get());
            return "redirect:/api/admin/index";
        } else {
            System.out.println("Administrador no encontrado con el correo y contraseña proporcionados.");
            return "redirect:/api/admin/";
        }
    }




    @GetMapping("/index")
    public String mostrarIndexAdmin(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            return "IndexAdmin"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }


}
