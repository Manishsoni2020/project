package oldpatientlogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/oldpt")
public class Oldpatientlogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Oldpatientlogin() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String contact=request.getParameter("contact");
		String url="jdbc:mysql://localhost:3306/hospitalmanagement";
		String user="root";
		String password="2002";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con =DriverManager.getConnection(url,user,password);
			String sql="select * from newpatient where pat_name=? and email=? and contact=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2,email);
			ps.setString(3, contact);
			// Execute the query
            ResultSet resultSet = ps.executeQuery();

            // Check if the email exists in the database
            if (resultSet.next()) {
                RequestDispatcher rd=request.getRequestDispatcher("last.html");
                rd.forward(request, response);
            } else {
            	RequestDispatcher rd=request.getRequestDispatcher("oldpatientreg.html");
                rd.include(request, response);
            }

            // Close the resources
            resultSet.close();
            ps.close();
            con.close();
			
			
		}
		catch(Exception ex)
		{
			out.println(ex);
		}
	}
}
