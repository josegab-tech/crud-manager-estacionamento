package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String path = req.getRequestURI().substring(req.getContextPath().length());

		boolean isLoginPage = path.equals("/login") || path.equals("/login.jsp");
		boolean isStaticResource = path.startsWith("/css/")
				|| path.startsWith("/js/")
				|| path.startsWith("/fonts/")
				|| path.startsWith("/images/");

		HttpSession session = req.getSession(false);
		boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

		if (isLoggedIn || isLoginPage || isStaticResource) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
}