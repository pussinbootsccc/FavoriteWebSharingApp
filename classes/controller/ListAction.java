/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;
import org.genericdao.RollbackException;
import databeans.FavoriteBean;
import databeans.UserBean;

//Browse the favorite websites for a given "user" after clicking the name on nav bar.
public class ListAction extends Action {
	//private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;
    public ListAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}
    public String getName() { return "list.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
		try {	
            // Set up user list of all users for nav bar
			request.setAttribute("userList",userDAO.getUsers());
			int userId;
			try {
				// Get the userId from the hyper-link.
				userId = Integer.parseInt(request.getParameter("userId"));
			} catch (NumberFormatException e) {
				errors.add("Invalid User Id");
				return "error.jsp";
			}
	        // Fetch the user bean by primary keys
        	UserBean user = userDAO.read(userId);
        	if (user == null) {
    			errors.add("Invalid User Id: " + userId);
    			return "error.jsp";
    		}
        	// record the given user
        	request.setAttribute("user", user);
        	FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(userId);
        	// record the given user favorites
	        request.setAttribute("favoriteList",favoriteList);
	        return "list.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
