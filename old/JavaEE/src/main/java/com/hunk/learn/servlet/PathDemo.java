package com.hunk.learn.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PathDemo
 */
@WebServlet("/PathDemo")
public class PathDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathDemo() {
        super();
        System.out.println(this.getClass().getName()+ " constructor ..");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("/").forward(request, response);
//		System.out.println("forward");
		
		response.sendRedirect(request.getContextPath()+"/servlet/adv.html");
		System.out.println("sendRedirect");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		System.out.println(this.getClass().getName()+ " init ..");
	}
	
	@Override
	public void destroy() {
		super.destroy();
		System.out.println(this.getClass().getName()+ " destroy ..");
	}
}
