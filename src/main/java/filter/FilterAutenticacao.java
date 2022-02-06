package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import database.SingleConnectionBanco;

//Filtrar todas as requisições vindas das paginas do diretório /principal/*
@WebFilter(urlPatterns = {"/principal/*"}) 
public class FilterAutenticacao extends HttpFilter implements Filter {
    
	private static Connection conn;
	
    public FilterAutenticacao() {
        super();
    }

    //Encerra os processos quando o servidor é parado
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Intercepta as requisições e respostas vindas no @WebFilter. Dar commit e rollback no banco
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String userLogado = (String) session.getAttribute("usuario");
			String urlAutenticar = req.getServletPath();
			
			if(userLogado == null || (userLogado != null && userLogado.isEmpty()) &&
					!urlAutenticar.contains("/principal/ServletLogin")) {
				RequestDispatcher disp = req.getRequestDispatcher("index.jsp?url=" + urlAutenticar);
				req.setAttribute("msg", "Por favor realize o login!");
				disp.forward(request, response);
				return;
			}else {
				chain.doFilter(request, response);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher disp = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			disp.forward(request, response);			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	//Inicia os processos/recursos quando o servidor inicia
	public void init(FilterConfig fConfig) throws ServletException {
		conn = SingleConnectionBanco.getConnection();
	}

}
