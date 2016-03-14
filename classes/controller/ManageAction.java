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
import databeans.FavoriteBean;
import databeans.UserBean;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;
public class ManageAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public ManageAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}
	public String getName() {
		return "manage.do";
	}
	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
		try {
            // Set up user list for nav bar.
			request.setAttribute("userList",userDAO.getUsers());
            // Fetch current user
			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			// Fetch favorites of the current user.
        	FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
	        request.setAttribute("favoriteList", favoriteList);
	        return "manage.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
	}
}
