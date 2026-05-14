package infraestructura.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dominio.modelo.Usuario;
import dominio.puertos.IUsuarioRepository;

public class SQLiteUsuarioRepository implements IUsuarioRepository {

    @Override
    public Usuario buscarPorCorreo(String correo) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try {

           Connection conn = Conexion.conectando();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setPasswordHash(rs.getString("password_hash"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        return null;
    }
}