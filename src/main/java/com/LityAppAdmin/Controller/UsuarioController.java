package com.LityAppAdmin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/lity")
public class UsuarioController {

    @GetMapping("/")
    public String showIndexUser() {
        return "IndexUsuario"; // Debe devolver el nombre de la vista sin extensión
    }

}
