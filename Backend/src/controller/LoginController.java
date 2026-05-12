package controller;

import aplicacion.UsuarioService;
import aplicacion.Sesion;
import dominio.modelo.Usuario;
import infraestructura.persistencia.SQLiteUsuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private final UsuarioService usuarioService =
        new UsuarioService(new SQLiteUsuarioRepository());

    // El login.js ya llama POST /login con { correo, password }
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        String correo   = body.get("correo");
        String password = body.get("password");

        Usuario u = usuarioService.login(correo, password);

        if (u != null) {
            Sesion.setUsuarioActual(u);
            return Map.of(
                "success", true,
                "nombre",  u.getNombre(),
                "idUsuario", u.getIdUsuario()
            );
        }

        return Map.of("success", false, "mensaje", "Correo o contraseña incorrectos");
    }

    @PostMapping("/logout")
    public Map<String, Boolean> logout() {
        Sesion.cerrarSesion();
        return Map.of("success", true);
    }
}