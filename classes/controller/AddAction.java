/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import formbeans.FavoriteForm;
import databeans.UserBean;
import databeans.FavoriteBean;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class AddAction extends Action {
    private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory
            .getInstance(FavoriteForm.class);
	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;
	
	public AddAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}
	public String getName() { return "add.do"; }
	
	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
        	request.setAttribute("userList", userDAO.getUsers());
        	// Fetch the current user and her favorite beans.
        	UserBean user = (UserBean) request.getSession(false).getAttribute("user");
        	FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
            
	        request.setAttribute("favoriteList",favoriteList);
	        
	        // Fetch info from the favorite form.
	        FavoriteForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0)
	        	return "manage.jsp";

	        // Add a favorite entry to the table of database.
            FavoriteBean bean = new FavoriteBean();
            bean.setUrl(form.getUrl());
            bean.setComment(form.getComment());
            bean.setClickCount(0);
            bean.setUserId(user.getUserId());
            // Create this favorite entry to the database.
            favoriteDAO.create(bean);
            return "manage.do";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "manage.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "manage.jsp";
        }
	}
}
