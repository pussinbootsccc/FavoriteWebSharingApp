/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.UserDAO;

public class LogoutAction extends Action {
	private UserDAO userDAO;
	public LogoutAction(Model model) {
		userDAO = model.getUserDAO();
	}
	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        // Set the session as null which means log out.
        HttpSession session = request.getSession(false);
        session.setAttribute("user",null);
        try {
        	request.setAttribute("userList", userDAO.getUsers());
    		request.setAttribute("message","You are now logged out");
            return "success.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
