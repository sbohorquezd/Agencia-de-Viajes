package aplicacion;

import dominio.modelo.Usuario;
import dominio.puertos.IUsuarioRepository;

public class UsuarioService {

    private IUsuarioRepository repo;

    public UsuarioService(IUsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario login(String correo, String passwordPlano) {

        Usuario u = repo.buscarPorCorreo(correo);

        if (u == null) return null;

        if (u.getPasswordHash().equals(passwordPlano)) {
            return u;
        }

        return null;
    }
}