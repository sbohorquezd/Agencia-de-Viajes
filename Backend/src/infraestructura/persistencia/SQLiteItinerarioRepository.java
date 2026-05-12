package infraestructura.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dominio.modelo.Itinerario;
import dominio.puertos.IItinerarioRepository;

public class SQLiteItinerarioRepository implements IItinerarioRepository {

    @Override
    public void guardar(Itinerario it) {
        String sql = "INSERT INTO itinerarios "
            + "(id_usuario, aeropuerto_salida, "
            + "aeropuerto_llegada, fecha_inicio, "
            + "fecha_fin, duracion_horas) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try {

           Connection conn = Conexion.conectando();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, it.getIdUsuario());

            ps.setString(2, it.getAeropuertoSalida());

            ps.setString(3, it.getAeropuertoLlegada());

            ps.setString(4, it.getFechaInicio());

            ps.setString(5, it.getFechaFin());

            ps.setDouble(6, it.getDuracionHoras());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Itinerario it) {

        String sql =
                "UPDATE itinerarios SET "
                + "aeropuerto_salida = ?, "
                + "aeropuerto_llegada = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ?, "
                + "duracion_horas = ? "
                + "WHERE id_itinerario = ?";

        try {

           Connection conn = Conexion.conectando();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, it.getAeropuertoSalida());

            ps.setString(2, it.getAeropuertoLlegada());

            ps.setString(3, it.getFechaInicio());

            ps.setString(4, it.getFechaFin());

            ps.setDouble(5, it.getDuracionHoras());

            ps.setInt(6, it.getIdItinerario());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int idItinerario) {

        String sql =
                "DELETE FROM itinerarios WHERE id_itinerario = ?";

        try {

            Connection conn = Conexion.conectando();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, idItinerario);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
public List<Itinerario> listarPorUsuario(int idUsuario) {

    List<Itinerario> lista = new ArrayList<>();

    String sql = "SELECT * FROM itinerarios WHERE id_usuario = ?";

    try {
        Connection conn = Conexion.conectando();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idUsuario);

        ResultSet rs = ps.executeQuery();  // ← agrega: import java.sql.ResultSet;

        while (rs.next()) {
            Itinerario it = new Itinerario();
            it.setIdItinerario(rs.getInt("id_itinerario"));
            it.setIdUsuario(rs.getInt("id_usuario"));
            it.setAeropuertoSalida(rs.getString("aeropuerto_salida"));
            it.setAeropuertoLlegada(rs.getString("aeropuerto_llegada"));
            it.setFechaInicio(rs.getString("fecha_inicio"));
            it.setFechaFin(rs.getString("fecha_fin"));
            it.setDuracionHoras(rs.getDouble("duracion_horas"));
            lista.add(it);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
}