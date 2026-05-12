package infraestructura.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static Connection instancia = null;

    private static final String cadena =
        "jdbc:sqlite:" + System.getProperty("user.dir") + "/Agencia_viajes.db" +
        "?busy_timeout=5000";

    public static Connection conectando() {
        try {
            if (instancia == null || instancia.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                instancia = DriverManager.getConnection(cadena);
                instancia.setAutoCommit(true);
            }
        } catch (Exception er) {
            System.err.println("Error de conexión: " + er.getMessage());
        }
        return instancia;
    }
}