package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Login;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletLogin() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		if(login.isEmpty() && senha.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e senha");
			dispatcher.forward(request, response);
		}else {
			Login loginUser = new Login(login, senha);
			
			if(login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin")) {
				request.getSession().setAttribute("usuario", loginUser.getLogin());
				
				if(url == null || url.equals("null")) {
					url = "principal/principal.jsp";
				}
				
				RequestDispatcher disp = request.getRequestDispatcher(url);
				disp.forward(request, response);
			}else {
				RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Login e/ou senha inválidos");
				disp.forward(request, response);
			}
		}
	}

}
