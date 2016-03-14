/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package formbeans;
import java.util.ArrayList;
import java.util.List;
import org.mybeans.form.FormBean;

public class RegisterForm extends FormBean {
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private String confirm ;
	
	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
	public String getEmailAddress()  { return emailAddress;  }
	public String getPassword()  { return password;  }
	public String getConfirm()   { return confirm;   }
	
	public void setFirstName(String s) { firstName = trimAndConvert(s,"<>\"");  }
	public void setLastName(String s)  { lastName  = trimAndConvert(s,"<>\"");  }
	public void setEmailAddress(String s)  { emailAddress  = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirm(String s)   { confirm   = s.trim();                  }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (firstName == null || firstName.length() == 0) {
			errors.add("First Name is required");
		}
		if (lastName == null || lastName.length() == 0) {
			errors.add("Last Name is required");
		}
		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("Email address is required");
		}
		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		if (confirm == null || confirm.length() == 0) {
			errors.add("Confirm Password is required");
		}
		if (errors.size() > 0) {
			return errors;
		}
		if (!password.equals(confirm)) {
			errors.add("Passwords are not the same");
		}
		return errors;
	}
}