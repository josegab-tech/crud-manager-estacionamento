package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.Vehicle;
import model.dao.DAOFactory;
import model.dao.VehicleDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/vehicles", "/vehicle/form", "/vehicle/delete", "/vehicle/insert", "/vehicle/update" })
public class VehiclesController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/vehicle/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-vehicle.jsp");
			break;
		}
		case "/crud-manager/vehicle/update": {
			CommonsController.listUsers(req);
			Vehicle v = loadVehicle(req);
			req.setAttribute("vehicle", v);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-vehicle.jsp");
			break;
		}
		default:
			listVehicles(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/vehicles.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/vehicles.jsp");
			return;
		}

		switch (action) {
		case "/crud-manager/vehicle/delete":
			deleteVehicle(req, resp);
			break;
		case "/crud-manager/vehicle/insert": {
			insertVehicle(req, resp);
			break;
		}
		case "/crud-manager/vehicle/update": {
			updateVehicle(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/vehicles");
	}

	private Vehicle loadVehicle(HttpServletRequest req) {
		String vehicleIdParameter = req.getParameter("vehicleId");
		int vehicleId = Integer.parseInt(vehicleIdParameter);

		VehicleDAO dao = DAOFactory.createDAO(VehicleDAO.class);

		try {
			Vehicle v = dao.findById(vehicleId);

			if (v == null)
				throw new ModelException("Veículo não encontrado para alteração");

			return v;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void updateVehicle(HttpServletRequest req, HttpServletResponse resp) {
		String vehiclePlate = req.getParameter("plate");
		String vehicleModel = req.getParameter("model");
		Integer vehicleManufactureYear = Integer.parseInt(req.getParameter("manufactureYear"));
		String vehicleCategory = req.getParameter("category");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Vehicle vehicle = loadVehicle(req);
		vehicle.setPlate(vehiclePlate);
		vehicle.setModel(vehicleModel);
		vehicle.setManufactureYear(vehicleManufactureYear);
		vehicle.setCategory(vehicleCategory);
		vehicle.setUser(new User(userId));

		VehicleDAO dao = DAOFactory.createDAO(VehicleDAO.class);

		try {
			if (dao.update(vehicle)) {
				ControllerUtil.sucessMessage(req, "Veículo '" + vehicle.getPlate() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Veículo '" + vehicle.getPlate() + "' não pôde ser atualizado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertVehicle(HttpServletRequest req, HttpServletResponse resp) {
		String vehiclePlate = req.getParameter("plate");
		String vehicleModel = req.getParameter("model");
		Integer vehicleManufactureYear = Integer.parseInt(req.getParameter("manufactureYear"));
		String vehicleCategory = req.getParameter("category");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Vehicle vehicle = new Vehicle();
		vehicle.setPlate(vehiclePlate);
		vehicle.setModel(vehicleModel);
		vehicle.setManufactureYear(vehicleManufactureYear);
		vehicle.setCategory(vehicleCategory);
		vehicle.setUser(new User(userId));

		VehicleDAO dao = DAOFactory.createDAO(VehicleDAO.class);

		try {
			if (dao.save(vehicle)) {
				ControllerUtil.sucessMessage(req, "Veículo '" + vehicle.getPlate() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Veículo '" + vehicle.getPlate() + "' não pôde ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteVehicle(HttpServletRequest req, HttpServletResponse resp) {
		String vehicleIdParameter = req.getParameter("id");
		int vehicleId = Integer.parseInt(vehicleIdParameter);

		VehicleDAO dao = DAOFactory.createDAO(VehicleDAO.class);

		try {
			Vehicle v = dao.findById(vehicleId);

			if (v == null)
				throw new ModelException("Veículo não encontrado para deleção");

			if (dao.delete(v)) {
				ControllerUtil.sucessMessage(req, "Veículo '" + v.getPlate() + "' deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Veículo '" + v.getPlate() + "' não pôde ser deletado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
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