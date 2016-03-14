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
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.UserBean;
import formbeans.IdForm;
public class DeleteAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

    public DeleteAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}
    public String getName() { return "delete.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
		try {
            // Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());
	    	IdForm form = formBeanFactory.create(request);
	    	UserBean user = (UserBean) request.getSession().getAttribute("user");
			// Get the favorite id to identify the favorite bean to be deleted.
	    	int id = form.getIdAsInt();
			favoriteDAO.delete(id, user.getUserId());
	        return "manage.do";
		} catch (RollbackException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
		} catch (FormBeanException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	}
    }
}


