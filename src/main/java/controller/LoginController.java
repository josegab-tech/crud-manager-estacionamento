package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.PasswordEncryptor;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		if (action.endsWith("/logout")) {
			HttpSession session = req.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			ControllerUtil.redirect(resp, req.getContextPath() + "/login");
			return;
		}

		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("usuarioLogado") != null) {
			ControllerUtil.redirect(resp, req.getContextPath() + "/index.jsp");
			return;
		}

		ControllerUtil.forward(req, resp, "/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("mail");
		String password = req.getParameter("password");

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);

		try {
			User user = dao.findByEmail(email);
			String encryptedPassword = PasswordEncryptor.encrypt(password);

			if (user != null && user.getPassword() != null && user.getPassword().equals(encryptedPassword)) {
				HttpSession session = req.getSession(true);
				session.setAttribute("usuarioLogado", user);
				ControllerUtil.redirect(resp, req.getContextPath() + "/index.jsp");
			} else {
				req.setAttribute("message", "E-mail ou senha inválidos.");
				ControllerUtil.forward(req, resp, "/login.jsp");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			req.setAttribute("message", "Erro ao realizar login: " + e.getMessage());
			ControllerUtil.forward(req, resp, "/login.jsp");
		}
	}
}