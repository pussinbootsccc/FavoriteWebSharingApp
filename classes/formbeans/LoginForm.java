/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package formbeans;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String emailAddress;
	private String password;
	
	public String getEmailAddress()  { return emailAddress; }
	public String getPassword()  { return password; }
	
	public void setEmailAddress(String s) { emailAddress = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s) {	password = s.trim();}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("User Name is required");
		}
		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}	
		return errors;
	}
}