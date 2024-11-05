package newstaffdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/st")
public class Newstaffdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Newstaffdata() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagement","root","2002");
			PreparedStatement ps=con.prepareStatement("insert into newstaff(st_name,father_name,contact,address,degree,specialization,join_date,email,pass,department) values(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1,request.getParameter("st_name"));
			ps.setString(2,request.getParameter("father_name"));
			ps.setString(3,request.getParameter("contact"));
			ps.setString(4,request.getParameter("address"));
			ps.setString(5,request.getParameter("degree"));
			ps.setString(6,request.getParameter("specialization"));
			ps.setString(7,request.getParameter("join_date"));
			ps.setString(8,request.getParameter("email"));
			ps.setString(9,request.getParameter("pass"));
			ps.setString(10,request.getParameter("department"));
			int status=ps.executeUpdate();
			System.out.print(status);
			con.close();
			
		}
		catch(Exception ex)
		{
			out.println(ex);
		}
	}
}
