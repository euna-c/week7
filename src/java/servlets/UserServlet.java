package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.UserService;
import services.RoleService;

public class UserServlet extends HttpServlet {

  /**
   * The doGet method in UserServlet, which works with users.jsp
   *
   * @param request request
   * @param response response
   * @throws ServletException Servlet Exception
   * @throws IOException Input/Output Exception
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    UserService us = new UserService();

    String email = request.getParameter("email");

    String action = request.getParameter("action");
    action = action == null ? "" : action;

    switch (action) {
      case "clearEdit": {
        request.setAttribute("edit", false);
        request.setAttribute("user", null);
        break;
      }
      case "edit": {
        if (checkIsValid(new String[]{email})) {
          request.setAttribute("edit", true);
          try {
            request.setAttribute("user", us.get(email));
          } catch (Exception e) {
            request.setAttribute("error", "Could not retrieve user.");
          }
        } else {
          request.setAttribute("error", "Could not retrieve user.");
          return;
        }
        break;
      }
      case "delete": {
        if (checkIsValid(new String[]{email})) {
          try {
            us.delete(email);
          } catch (Exception e) {
            request.setAttribute("error", "Could not delete user.");
          }
        } else {
          request.setAttribute("error", "Could not delete user.");
          return;
        }
        break;
      }
    } 
    
    try {
      request.setAttribute("users", us.getAll());
    } catch (Exception e) {
      request.setAttribute("error", e.getMessage());
    }
    //Originally this shoud be in RoleServlet.... 
    RoleService rs = new RoleService();
    try {
        //this "roles" is called in jsp dropbox forEach condition as item="${roles}
      request.setAttribute("roles", rs.getAll());
    } catch (Exception e) {
      request.setAttribute("error", e.getMessage());
    }

    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
  }

  /**
   * The doPost method in UserServlet, which works with users.jsp
   *
   * @param request request
   * @param response response
   * @throws ServletException Servlet Exception
   * @throws IOException IOException
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    UserService us = new UserService();

    String email = request.getParameter("email");
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String password = request.getParameter("password");
    //check
    //String roleString = request.getParameter("role");
   
    int roleID = Integer.parseInt(request.getParameter("rolerole"));
    
    
    String action = request.getParameter("action");
    action = action == null ? "" : action;
    
    

    try {
      switch (action) {
        case "add":
          if (checkIsValid(new String[]{email, fname, lname, password})) {
            us.insert(email, fname, lname, password, roleID);
          } else 
          {
            request.setAttribute("error", "All fields are required");
          }
        case "edit":
          if (checkIsValid(new String[]{email, fname, lname, password})) 
          {
            us.update(email, fname, lname, password, roleID);
          } 
          else 
          {
            request.setAttribute("error", "All fields are required");
          }
      }
    } catch (Exception ex) {
      request.setAttribute("error", ex.getMessage());
    }

    try {
      request.setAttribute("users", us.getAll());
    } catch (Exception ex) {
      request.setAttribute("error", ex.getMessage());
    }

    getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
            .forward(request, response);
  }

  /**
   * Goes through values and ensures they are all not null or empty
   *
   * @param values values
   * @return boolean if all values are valid
   */
  private boolean checkIsValid(String[] values) {
    // check each elemenet in array for null or empty string
    // return false if one is found
    for (String s : values) {
      if (s == null || s.equals("")) {
        return false;
      }
    }
    return true;
  }
}
