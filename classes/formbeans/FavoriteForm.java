/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package formbeans;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
    private String url;
    private String comment;
    
    public String getUrl() { return url; }
    public String getComment() { return comment; }
    
    public void setUrl(String _url) { url = sanitize(_url); }
    public void setComment(String _comment) { comment = sanitize(_comment); }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if (url == null || url.length() == 0) {
            errors.add("URL is required");
        }
        if (comment == null || comment.length() == 0) {
            errors.add("Comment is required");
        }
        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
