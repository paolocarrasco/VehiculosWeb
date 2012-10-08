package pe.com.abaris.vehiculos.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.com.abaris.vehiculos.gestores.GestorVehiculoImpl;
import pe.com.abaris.vehiculos.modelo.Vehiculo;

/**
 * Gestiona el listado de vehiculos
 */
public class ListadoVehiculosServlet extends HttpServlet {

    GestorVehiculoImpl gestorVehiculo = new GestorVehiculoImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Vehiculo> listadoVehiculos = gestorVehiculo.listar();
        req.setAttribute("vehiculos", listadoVehiculos);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/listadoVehiculos.jsp");
        requestDispatcher.forward(req, resp);
    }

}
