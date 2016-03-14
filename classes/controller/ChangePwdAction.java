/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.UserBean;
import formbeans.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private UserDAO userDAO;
	public ChangePwdAction(Model model) {
		userDAO = model.getUserDAO();
	}
	public String getName() { return "change-pwd.do"; }
    // Perform change password action.
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
            // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());
	        // Load the form parameters into a form bean
	        ChangePwdForm form = formBeanFactory.create(request);
	        if (!form.isPresent()) {
	            return "change-pwd.jsp";
	        }
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "change-pwd.jsp";
	        }
	        // Fetch the current user.
			UserBean user = (UserBean) request.getSession().getAttribute("user");
			// Methods needs two parameters: email and new password.
        	userDAO.setPassword(user.getEmailAddress(),form.getNewPassword());
			request.setAttribute("message","Password changed for "+user.getEmailAddress());
	        return "success.jsp";
        } catch (RollbackException e) {
        	errors.add(e.toString());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.toString());
        	return "error.jsp";
        }
    }
}
