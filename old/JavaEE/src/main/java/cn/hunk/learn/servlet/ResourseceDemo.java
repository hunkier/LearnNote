package cn.hunk.learn.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 读取web应用下的资源文件（例如properties）
 * Servlet implementation class ResourseceDemo
 */
@WebServlet("/ResourseceDemo")
public class ResourseceDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResourseceDemo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 *  . 代表java命令运行目录。java运行命令在哪里？？ 在tomcat/bin目录下
		 *   结论： 在web项目中， . 代表在tomcat/bin目录下开始，所以不能使用这种相对路径。
		 */
		
		//读取文件。在web项目下不要这样读取。因为.表示在tomcat/bin目录下
				/*File file = new File("./src/db.properties");
				FileInputStream in = new FileInputStream(file);*/
		
		/**
		 * 使用web应用下加载资源文件的方法
		 */
		/**
		 * 1. getRealPath读取,返回资源文件的绝对路径
		 */
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/db.properties");
		System.out.println(path);
		File file = new File(path);
		FileInputStream in = new FileInputStream(file);
		/*
		String path  = ResourseceDemo.class.getResource("/db.properties").getPath();
		System.out.println(path);
		InputStream in  = ResourseceDemo.class.getResourceAsStream("/db.properties");
		*/
		
		Properties prop = new Properties();
		prop.load(in);
		String password = prop.getProperty("password");
		String user = prop.getProperty("user");
		PrintWriter writer = response.getWriter();
		writer.println("path: " + path );
		writer.println("user: " + user );
		writer.println("password: " + password );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
