package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Students")
public class StudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //Anslut till databasen och hämta data
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/gritacademy";

            //using this part to set my values log in values from the index.html page using the forms
            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM students";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            out.println("<link rel=\"stylesheet\" href=\"/style.css\">");
            out.println("<html><body class=\"body\">");

            //Skriv ut resultatet till webbläsaren
            out.println("<h1>Students Data:</h1>");

            while (resultSet.next()) {
                out.println("<h4>PK: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("fname") +" "+ resultSet.getString("lname")
                        + ", Town: " + resultSet.getString("town") + ", Hobby: " + resultSet.getString("hobby") + "</h4>");
            }
            out.println("<a href=\"index.html\">Home Page</a>");
            out.println("<a href=\"Courses\">Courses</a>");
            out.println("<a href=\"Attendance\">Attendance</a>");
            out.println("</body></html>");

            //Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            //redirection back to index.html (the login page), in a perfect world maybe check the inputs before this
            response.sendRedirect("/index.html");
            System.out.println(e.getMessage());
            out.println("<p><span style= \"background-color:red\">Error: " + e.getMessage() + "</span></p>");
        } finally {
            out.close();
        }
    }
}
