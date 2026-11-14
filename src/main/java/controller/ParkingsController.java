package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/parkings", "/parking/form", "/parking/delete", "/parking/insert", "/parking/update"}) 

public class ParkingsController extends HttpServlet {
	
}
