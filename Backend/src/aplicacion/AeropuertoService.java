package aplicacion;

import dominio.modelo.Aeropuerto;
import dominio.puertos.IAeropuertoService;
import java.util.List;

public class AeropuertoService {

    private IAeropuertoService servicio;

    public AeropuertoService(IAeropuertoService servicio) {
        this.servicio = servicio;
    }

    public List<Aeropuerto> buscar(String nombre) {
        return servicio.buscarAeropuertos(nombre);
    }
}