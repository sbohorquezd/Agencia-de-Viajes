package dominio.puertos;

import dominio.modelo.Aeropuerto;
import java.util.List;

public interface IAeropuertoService {

    List<Aeropuerto> buscarAeropuertos(String nombre);

    Aeropuerto buscarPorCodigo(String codigoIata);
}