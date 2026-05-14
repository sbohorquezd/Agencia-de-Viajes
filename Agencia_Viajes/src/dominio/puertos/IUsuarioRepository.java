package dominio.puertos;

import dominio.modelo.Usuario;

public interface IUsuarioRepository {

    Usuario buscarPorCorreo(String correo);

    Usuario buscarPorId(int idUsuario);
}