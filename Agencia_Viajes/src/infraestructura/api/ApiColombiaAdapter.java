package infraestructura.api;

import dominio.modelo.Aeropuerto;
import dominio.puertos.IAeropuertoService;
import java.util.ArrayList;
import java.util.List;

public class ApiColombiaAdapter implements IAeropuertoService {

    @Override
    public List<Aeropuerto> buscarAeropuertos(String nombre) {
        return new ArrayList<>();
    }

    @Override
    public Aeropuerto buscarPorCodigo(String codigoIata) {
        return null;
    }
}