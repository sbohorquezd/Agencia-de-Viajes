package aplicacion;

import dominio.modelo.Itinerario;
import dominio.puertos.IItinerarioRepository;

public class ItinerarioService {

    private IItinerarioRepository repo;

    public ItinerarioService(IItinerarioRepository repo) {
        this.repo = repo;
    }

    public void guardar(Itinerario it) {
        repo.guardar(it);
    }
    
    public void eliminar(int idItinerario) {

        repo.eliminar(idItinerario);
    }
    
    public void actualizar(Itinerario it) {

        repo.actualizar(it);
    }
}