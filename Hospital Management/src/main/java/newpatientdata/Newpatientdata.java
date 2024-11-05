package newpatientdata;

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

@WebServlet("/newpatientdata")
public class Newpatientdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Newpatientdata() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagement","root","2002");
			PreparedStatement pt=con.prepareStatement("insert into newpatient(pat_name,father_name,mother_name,age,address,diseases,email,pass,contact) values(?,?,?,?,?,?,?,?,?)");
			pt.setString(1,request.getParameter("pat_name"));
			pt.setString(2,request.getParameter("father_name"));
			pt.setString(3,request.getParameter("mother_name"));
			pt.setString(4,request.getParameter("age"));
			pt.setString(5,request.getParameter("address"));
			pt.setString(6,request.getParameter("diseases"));
			pt.setString(7,request.getParameter("email"));
			pt.setString(8,request.getParameter("pass"));
			pt.setString(9,request.getParameter("contact"));
			int status=pt.executeUpdate();
			System.out.print(status);
			con.close();
			
		}
		catch(Exception ex)
		{
			out.println(ex);
		}
	}
}
