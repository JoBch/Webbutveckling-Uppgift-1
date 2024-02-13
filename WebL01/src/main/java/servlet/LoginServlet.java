package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Input credentials to check
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession(true);
        session.setAttribute("username", username);
        session.setAttribute("password", password);

        response.sendRedirect("/login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out =response.getWriter();

        out.println("<link rel=\"stylesheet\" href=\"/style.css\">");
        out.println("<html><body class=\"body\">");
        out.println("<title>Hello Servlet</title>");
        out.println("<h2>Hello from Java Servlet!</h2>");
        out.println("<a href=\"Students\">Students</a>");
        out.println("<a href=\"Courses\">Courses</a>");
        out.println("<a href=\"Attendance\">Attendance</a>");
        out.println("</body></html>");
    }
}