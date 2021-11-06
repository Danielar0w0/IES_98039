package pt.ua.deti.ies.lab2.firstProject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyFirstServlet", urlPatterns = {"/MyFirstServlet"})
public class MyFirstServlet extends HttpServlet {
 
    private static final long serialVersionUID = -1915463532411657451L;
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");

        if (username == null)
            throw new NullPointerException("Please provide a username.");

        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>MyFirstServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.print("<h1>Hello ");

            // if (username != null)
            //     out.print(username);
            // else
            //     out.print("World");
            out.print(username);

            out.println("!</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
