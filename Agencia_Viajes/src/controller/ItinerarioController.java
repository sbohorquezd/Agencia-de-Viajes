package controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aplicacion.ItinerarioService;
import dominio.modelo.Itinerario;
import infraestructura.persistencia.SQLiteItinerarioRepository;

@RestController
@RequestMapping("/itinerarios")
@CrossOrigin(origins = "*")
public class ItinerarioController {

    private final ItinerarioService service =
        new ItinerarioService(new SQLiteItinerarioRepository());

    // GET /itinerarios?idUsuario=1
    @GetMapping
    public List<Itinerario> listar(@RequestParam int idUsuario) {
        SQLiteItinerarioRepository repo = new SQLiteItinerarioRepository();
        return repo.listarPorUsuario(idUsuario);
    }

@PostMapping
public Map<String, Boolean> guardar(@RequestBody Map<String, Object> body) {
    try {
        Itinerario it = new Itinerario();
        it.setIdUsuario(Integer.parseInt(body.get("idUsuario").toString()));
        it.setAeropuertoSalida((String) body.get("salida"));
        it.setAeropuertoLlegada((String) body.get("llegada"));
        it.setFechaInicio((String) body.get("fechaInicio"));
        it.setFechaFin((String) body.get("fechaFin"));
        it.setDuracionHoras(Double.parseDouble(body.get("duracion").toString()));
        service.guardar(it);
        return Map.of("success", true);
    } catch (Exception e) {
        System.err.println("ERROR AL GUARDAR: " + e.getMessage());
        e.printStackTrace();
        return Map.of("success", false);
    }
}
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminar(@PathVariable int id) {
        service.eliminar(id);
        return Map.of("success", true);
    }

@PutMapping("/{id}")
public Map<String, Boolean> actualizar(@PathVariable int id, @RequestBody Map<String, Object> body) {
    try {
        Itinerario it = new Itinerario();
        it.setIdItinerario(id);
        it.setIdUsuario(((Number) body.get("idUsuario")).intValue());
        it.setAeropuertoSalida((String) body.get("aeropuertoSalida"));
        it.setAeropuertoLlegada((String) body.get("aeropuertoLlegada"));
        it.setFechaInicio((String) body.get("fechaInicio"));
        it.setFechaFin((String) body.get("fechaFin"));
        it.setDuracionHoras(((Number) body.get("duracionHoras")).doubleValue());
        service.actualizar(it);
        return Map.of("success", true);
    } catch (Exception e) {
        e.printStackTrace();
        return Map.of("success", false);
    }
}
}