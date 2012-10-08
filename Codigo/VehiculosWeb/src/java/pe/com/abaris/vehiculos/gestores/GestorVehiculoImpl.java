package pe.com.abaris.vehiculos.gestores;

import java.util.List;
import pe.com.abaris.vehiculos.accesodatos.VehiculoDaoImpl;
import pe.com.abaris.vehiculos.modelo.Vehiculo;

/**
 * Contiene la logica relacionada a los vehiculos
 */
public class GestorVehiculoImpl {
    
    private VehiculoDaoImpl vehiculoDao = new VehiculoDaoImpl();

    public List<Vehiculo> listar() {
        return vehiculoDao.buscar();
    }
}
