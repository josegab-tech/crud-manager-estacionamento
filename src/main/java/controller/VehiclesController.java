package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Vehicle;
import model.dao.DAOFactory;
import model.dao.VehicleDAO;

@SuppressWarnings("serial")

@WebServlet(urlPatterns = { "/vehicles"})
public class VehiclesController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		
		default:
			listVehicles(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/vehicles.jsp");
		}
		}
			
			
	
	
	
	private void listVehicles(HttpServletRequest req) {
	VehicleDAO dao = DAOFactory.createDAO(VehicleDAO.class);
	
	List<Vehicle> vehicles = null;
	try {
		vehicles = dao.listAll();
	} catch (ModelException e) {
		e.printStackTrace();
	}
	
	if (vehicles != null)
		req.setAttribute("vehicles", vehicles);
}
}
