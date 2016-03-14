/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import databeans.FavoriteBean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.FavoriteDAO;
import model.Model;

public class ClickAction extends Action {
	private FavoriteDAO favoriteDAO;
	public ClickAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
	}
	public String getName() {
		return "click.do";
	}
    // Perform click action.
	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
		int favoriteId = Integer.parseInt((String) request.getParameter("favoriteId"));
    	try {
	    	// original read method that takes primary key
			FavoriteBean bean = favoriteDAO.read(favoriteId);
			favoriteDAO.clickPlusOne(favoriteId);
			// Identify the URL with suffix for send redirect.
			return bean.getUrl() + ".go";
    	} catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
	}
}
