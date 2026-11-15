package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Parking;
import model.Vehicle;
import model.dao.DAOFactory;
import model.dao.ParkingDAO;
import model.dao.VehicleDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/parkings", "/parking/form", "/parking/delete", "/parking/insert", "/parking/update" })
public class ParkingsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/parking/form": {
			listVehicles(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-parking.jsp");
			break;
		}
		case "/crud-manager/parking/update": {
			listVehicles(req);
			Parking p = loadParking(req);
			req.setAttribute("parking", p);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-parking.jsp");
			break;
		}
		default:
			listParkings(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/parkings.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/parkings.jsp");
			return;
		}

		switch (action) {
		case "/crud-manager/parking/delete":
			deleteParking(req, resp);
			break;
		case "/crud-manager/parking/insert": {
			insertParking(req, resp);
			break;
		}
		case "/crud-manager/parking/update": {
			updateParking(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/parkings");
	}

	private Parking loadParking(HttpServletRequest req) {
		String parkingIdParameter = req.getParameter("parkingId");
		int parkingId = Integer.parseInt(parkingIdParameter);

		ParkingDAO dao = DAOFactory.createDAO(ParkingDAO.class);

		try {
			Parking p = dao.findById(parkingId);

			if (p == null)
				throw new ModelException("Registro de estacionamento não encontrado para alteração");

			return p;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void updateParking(HttpServletRequest req, HttpServletResponse resp) {
		Integer spotNumber = Integer.parseInt(req.getParameter("spotNumber"));
		
		String entryTimeParam = req.getParameter("entryTime");
		LocalDateTime entryTime = (entryTimeParam != null && !entryTimeParam.isEmpty()) 
				? LocalDateTime.parse(entryTimeParam) 
				: null;

		BigDecimal hourlyRate = new BigDecimal(req.getParameter("hourlyRate"));
		boolean isOccupied = Boolean.parseBoolean(req.getParameter("isOccupied"));
		
		String vehicleParam = req.getParameter("vehicle");
		Vehicle vehicle = (vehicleParam != null && !vehicleParam.isEmpty()) 
				? new Vehicle(Integer.parseInt(vehicleParam)) 
				: null;

		Parking parking = loadParking(req);
		parking.setSpotNumber(spotNumber);
		parking.setEntryTime(entryTime);
		parking.setHourlyRate(hourlyRate);
		parking.setOccupied(isOccupied);
		parking.setVehicle(vehicle);

		ParkingDAO dao = DAOFactory.createDAO(ParkingDAO.class);

		try {
			if (dao.update(parking)) {
				ControllerUtil.sucessMessage(req, "Vaga '" + parking.getSpotNumber() + "' atualizada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Vaga '" + parking.getSpotNumber() + "' não pôde ser atualizada.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertParking(HttpServletRequest req, HttpServletResponse resp) {
		Integer spotNumber = Integer.parseInt(req.getParameter("spotNumber"));
		
		String entryTimeParam = req.getParameter("entryTime");
		LocalDateTime entryTime = (entryTimeParam != null && !entryTimeParam.isEmpty()) 
				? LocalDateTime.parse(entryTimeParam) 
				: null;

		BigDecimal hourlyRate = new BigDecimal(req.getParameter("hourlyRate"));
		boolean isOccupied = Boolean.parseBoolean(req.getParameter("isOccupied"));
		
		String vehicleParam = req.getParameter("vehicle");
		Vehicle vehicle = (vehicleParam != null && !vehicleParam.isEmpty()) 
				? new Vehicle(Integer.parseInt(vehicleParam)) 
				: null;

		Parking parking = new Parking();
		parking.setSpotNumber(spotNumber);
		parking.setEntryTime(entryTime);
		parking.setHourlyRate(hourlyRate);
		parking.setOccupied(isOccupied);
		parking.setVehicle(vehicle);

		ParkingDAO dao = DAOFactory.createDAO(ParkingDAO.class);

		try {
			if (dao.save(parking)) {
				ControllerUtil.sucessMessage(req, "Vaga '" + parking.getSpotNumber() + "' registrada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Vaga '" + parking.getSpotNumber() + "' não pôde ser registrada.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteParking(HttpServletRequest req, HttpServletResponse resp) {
		String parkingIdParameter = req.getParameter("id");
		int parkingId = Integer.parseInt(parkingIdParameter);

		ParkingDAO dao = DAOFactory.createDAO(ParkingDAO.class);

		try {
			Parking p = dao.findById(parkingId);

			if (p == null)
				throw new ModelException("Registro de estacionamento não encontrado para deleção");

			if (dao.delete(p)) {
				ControllerUtil.sucessMessage(req, "Vaga '" + p.getSpotNumber() + "' deletada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Vaga '" + p.getSpotNumber() + "' não pôde ser deletada.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listParkings(HttpServletRequest req) {
		ParkingDAO dao = DAOFactory.createDAO(ParkingDAO.class);

		int page = 1;
		String pageParam = req.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			try {
				page = Integer.parseInt(pageParam);
				if (page < 1) page = 1;
			} catch (NumberFormatException e) {
				page = 1;
			}
		}

		int limit = 5;
		int offset = (page - 1) * limit;

		List<Parking> parkings = null;
		int totalRecords = 0;
		try {
			parkings = dao.listAll(limit, offset);
			totalRecords = dao.countAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}

		int totalPages = (int) Math.ceil((double) totalRecords / limit);
		if (totalPages < 1) totalPages = 1;

		if (parkings != null) {
			req.setAttribute("parkings", parkings);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
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