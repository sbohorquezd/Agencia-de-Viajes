package dominio.puertos;

import dominio.modelo.Itinerario;
import infraestructura.persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface IItinerarioRepository {

    void guardar(Itinerario it);

    void actualizar(Itinerario it);

    void eliminar(int idItinerario);;

    List<Itinerario> listarPorUsuario(int idUsuario);
    
}