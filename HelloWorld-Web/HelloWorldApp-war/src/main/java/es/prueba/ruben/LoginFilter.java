package es.prueba.ruben;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class LoginFilter implements Filter{

	private static final Logger logger = Logger.getLogger(LoginFilter.class);	
	//@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		logger.debug("INICIANDO LOGINFILTER");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// Si el usuario no está logado o el id no es el de la sesión activa, se redirige a la página de login
		if(isAuthorizedRequest(req)){
			chain.doFilter(request, response);
		}else{
			logger.warn("Unauthorized request");
			res.sendRedirect(req.getContextPath());
		}		
	}
	

	private boolean isAuthorizedRequest(HttpServletRequest req) {
//		String jsessionID = null;
//		Cookie[] cookies = req.getCookies();
//		if(cookies !=null){
//			for(Cookie cookie : cookies){
//				if(cookie.getName().equals("JSESSIONID")){
//					jsessionID = cookie.getValue();
//					break;
//				}	 
//			}
//		}
//		logger.warn("JSESSIONID:"+jsessionID);
		return false;
	}


	//@Override
	public void init(FilterConfig arg0) throws ServletException {
		// No se hace nada al iniciar el objeto.
	}
	
	//@Override
	public void destroy() {
		// No se hace nada al destruir el objeto.
	}
}
