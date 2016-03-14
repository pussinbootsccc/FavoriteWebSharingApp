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
import model.Model;
import model.UserDAO;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import databeans.UserBean;
import formbeans.RegisterForm;

public class RegisterAction extends Action {
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);
	private UserDAO userDAO;
	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}
	public String getName() { return "register.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
	        RegisterForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        request.setAttribute("userList", userDAO.getUsers());

	        if (!form.isPresent()) {
	            return "register.jsp";
	        }       
        	// check if the email address already exists
        	if (form.getEmailAddress() != null) {
        		UserBean u = new UserBean();
	            u = userDAO.read(form.getEmailAddress());
	            if (u != null) {
	            	errors.add("This email address has already been registered.");
	            }
        	}
	        // Check validation errors.
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "register.jsp";
	        }
	        // Create the user bean
	        UserBean user = new UserBean();
	        user.setEmailAddress(form.getEmailAddress());
	        user.setFirstName(form.getFirstName());
	        user.setLastName(form.getLastName());
	        user.setPassword(form.getPassword());
        	userDAO.create(user);// with new auto-increment user id
            // after creation only the copy in database whose userId has been updated
            // fetch the user (with updated primary key) from the database by unique email address
            UserBean updatedUser = userDAO.read(user.getEmailAddress());
			// Attach (this copy of) the user bean to the session
	        HttpSession session = request.getSession(false);
	        session.setAttribute("user",updatedUser);
			return "manage.do";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "register.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "register.jsp";
        }
    }
}
