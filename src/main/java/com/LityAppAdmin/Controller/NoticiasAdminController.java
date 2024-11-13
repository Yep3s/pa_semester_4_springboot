package com.LityAppAdmin.Controller;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Model.NoticiaModel;
import com.LityAppAdmin.Repository.INoticiaRepository;
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
public class NoticiasAdminController {

    @Autowired
    private INoticiaRepository noticiaRepository;

    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    @GetMapping("/crear-noticia")
    public String mostrarCrearNoticia(HttpSession session, Model model) {
        if (session.getAttribute("adminCorreo") != null) {
            model.addAttribute("noticia", new NoticiaModel());
            return "CrearNoticia"; // Nombre del archivo HTML sin la extensión
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    @PostMapping("/crear-noticia")
    public String crearNoticia(HttpSession session, NoticiaModel noticia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "CrearNoticia";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        noticia.setCorreoAdministrador(correoAdministrador);
        noticiaRepository.save(noticia);
        return "redirect:/api/admin/ver-noticias";

    }

    @GetMapping("/ver-noticias")
    public String showNoticias(Model model) {
        model.addAttribute("noticias", noticiaRepository.findAll());
        return "VerNoticias";
    }

    @GetMapping("/editar-noticia/{id}")
    public String mostrarFormularioEdicionNoticia(@PathVariable("id") long id, Model model) {
        NoticiaModel noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("noticia", noticia);
        return "EditarNoticia";
    }

    @PostMapping("/actualizar-noticia/{id}")
    public String updateNoticia(@PathVariable("id") long id, NoticiaModel noticia,
                             BindingResult result, Model model,HttpSession session) {
        if (result.hasErrors()) {
            noticia.setId(id);
            return "EditarGuiaRapida";
        }

        String correoAdministrador;
        try {
            correoAdministrador = obtenerCorreoAdministrador(session);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "No hay un administrador en sesión.");
            return "/api/admin/IndexAdmin";
        }
        noticia.setCorreoAdministrador(correoAdministrador);

        if (noticia.getImage() == null || noticia.getImage().isEmpty()) {
            NoticiaModel imagenExistente = noticiaRepository.findById(id).orElse(null);
            if (imagenExistente != null) {
                noticia.setImage(imagenExistente.getImage()); // Mantener la ruta de la imagen anterior
            }
        }

        noticiaRepository.save(noticia);
        return "redirect:/api/admin/ver-noticias";
    }


    @GetMapping("/eliminar-noticia/{id}")
    public String deleteGuia(@PathVariable("id") long id, Model model) {
        NoticiaModel noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        noticiaRepository.delete(noticia);
        return "redirect:/api/admin/ver-noticias";
    }





}
