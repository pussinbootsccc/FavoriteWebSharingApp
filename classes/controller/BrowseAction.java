/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import model.Model;
import model.UserDAO;

public class BrowseAction extends Action {
	private UserDAO userDAO;
	public BrowseAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "browse.do";
	}

	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
		try {
			request.setAttribute("userList", userDAO.getUsers());
			return "browse.jsp";
		} catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
	}
}
