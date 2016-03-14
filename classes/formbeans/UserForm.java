/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package formbeans;
import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String userId = "";
	public String getUserId()  { return userId; }
	public void setUserId(String s)  { userId = trimAndConvert(s,"<>>\"]"); }
}
