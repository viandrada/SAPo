package com.sapo.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sapo.beans.LoginBean;

public class LoginFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Get the loginBean from session attribute
		LoginBean loginBean = (LoginBean) ((HttpServletRequest) request)
				.getSession().getAttribute("loginBean");

		// For the first application request there is no loginBean in the
		// session so user needs to log in
		// For other requests loginBean is present but we need to check if user
		// has logged in successfully
		//String contextPath = ((HttpServletRequest) request).getContextPath();

		String path = ((HttpServletRequest) request).getRequestURI();

		if (path.endsWith("/login.xhtml") || path.contains("resource")) {
			chain.doFilter(request, response); // Just continue chain.
		} else {
			if (loginBean == null || !loginBean.isLogueado()) {

				// ((HttpServletResponse) response).sendRedirect(contextPath
				// + "/login.xhtml");
				chain.doFilter(request, response);
				String newPath = "login.xhtml";
				request.getRequestDispatcher(newPath)
						.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}
}
