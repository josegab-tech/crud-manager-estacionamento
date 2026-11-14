package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Message;
import model.User;
import model.dao.DAOFactory;
import model.dao.MessageDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/messages", "/message/form", "/message/delete", "/message/insert", "/message/update"})
public class MessagesController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/message/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-message.jsp");
			break;
		}
		case "/crud-manager/message/update": {
			CommonsController.listUsers(req);
			Message p = loadMessage(req);
			req.setAttribute("message", p);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-message.jsp");
			break;
		}
		default:
			listMessages(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/messages.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/messages.jsp");
			return;
		}
		
		switch (action) {
		case "/crud-manager/message/delete":
			deleteMessage(req, resp);
			break;
		case "/crud-manager/message/insert": {
			insertMessage(req, resp);
			break;
		}
		case "/crud-manager/message/update": {
			updateMessage(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/messages");
	}
	
	private Message loadMessage(HttpServletRequest req) {
		String messageIdParameter = req.getParameter("messageId");
		int messageId = Integer.parseInt(messageIdParameter);
		
		MessageDAO dao = DAOFactory.createDAO(MessageDAO.class);
		
		try {
			Message p = dao.findById(messageId);
			
			if (p == null)
				throw new ModelException("Mensagem não encontrada para alteração");
			
			return p;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateMessage(HttpServletRequest req, HttpServletResponse resp) {
		String messageContent = req.getParameter("content");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Message message = loadMessage(req);
		message.setContent(messageContent);
		message.setUser(new User(userId));
		
		MessageDAO dao = DAOFactory.createDAO(MessageDAO.class);
		
		try {
			if (dao.update(message)) {
				ControllerUtil.sucessMessage(req, "Mensagem '" + message.getContent() + "' atualizada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Mensagem '" + message.getContent() + "' não pôde ser atualizada.");
			}				
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertMessage(HttpServletRequest req, HttpServletResponse resp) {
		String messageContent = req.getParameter("content");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Message message = new Message();
		message.setContent(messageContent);
		message.setPostDate(Calendar.getInstance().getTime());
		message.setUser(new User(userId));
		
		MessageDAO dao = DAOFactory.createDAO(MessageDAO.class);
		
		try {
			if (dao.save(message)) {
				ControllerUtil.sucessMessage(req, "Mensagem '" + message.getContent() + "' salva com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Mensagem '" + message.getContent() + "' não pôde ser salva.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteMessage(HttpServletRequest req, HttpServletResponse resp) {
		String messageIdParameter = req.getParameter("id");
		int messageId = Integer.parseInt(messageIdParameter);
		
		MessageDAO dao = DAOFactory.createDAO(MessageDAO.class);
		
		try {
			Message p = dao.findById(messageId);
			
			if (p == null)
				throw new ModelException("Mensagem não encontrada para deleção");
			
			if (dao.delete(p)) {
				ControllerUtil.sucessMessage(req, "Mensagem '" + p.getContent() + "' deletada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Mensagem '" + p.getContent() + "' não pôde ser deletada.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listMessages(HttpServletRequest req) {
		MessageDAO dao = DAOFactory.createDAO(MessageDAO.class);
		
		List<Message> messages = null;
		try {
			messages = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (messages != null)
			req.setAttribute("messages", messages);
	}
}