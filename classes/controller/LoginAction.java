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
import formbeans.LoginForm;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	private UserDAO userDAO;
	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}
	public String getName() { return "login.do"; }
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);  
        try {
	    	LoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        request.setAttribute("userList", userDAO.getUsers());
            // Present empty form for new comer.
	        if (!form.isPresent()) {
	            return "login.jsp";
	        }
	        // Check validation errors
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "login.jsp";
	        }
	        // Look up the user by enter email address.
	        UserBean user = userDAO.read(form.getEmailAddress());
	        if (user == null) {
	            errors.add("User email address not found");
	            return "login.jsp";
	        }
	        // Check the password
	        if (!user.checkPassword(form.getPassword())) {
	            errors.add("Incorrect password");
	            return "login.jsp";
	        }
	        // Attach (this copy of) the user bean to the session
	        HttpSession session = request.getSession();
	        session.setAttribute("user",user);
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
