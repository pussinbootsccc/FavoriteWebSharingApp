/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package model;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import databeans.FavoriteBean;
import databeans.UserBean;

public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			userDAO  = new UserDAO("yujiecha_user", pool);
			favoriteDAO = new FavoriteDAO("yujiecha_favorite", pool);	
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
	// To pre-load users and their favorites after creating the table for the first time.
	public void preload() throws ServletException {
		try {
			int numUsers = userDAO.getCount();
			if (numUsers == 0) {
				
				preloadUser("yujiecha@gmail.com", "Yujie", "Cha", "cyj");
				preloadUser("trump@gmail.com", "Donald", "Trump", "fired");
				preloadUser("obama@gmail.com", "Barack", "Obama", "cando");
				
				preloadFavorite("yujiecha@gmail.com", "www.google.com", "Google");
				preloadFavorite("yujiecha@gmail.com", "www.yahoo.com", "Yahoo!");
				preloadFavorite("yujiecha@gmail.com", "www.w3schools.com", "Online web tutorial");
				preloadFavorite("yujiecha@gmail.com", "www.cmu.edu", "CMU");
				
				preloadFavorite("trump@gmail.com", "www.youtube.com", "Youtube");
				preloadFavorite("trump@gmail.com", "www.cnn.com", "CNN");
				preloadFavorite("trump@gmail.com", "www.nytimes.com", "The NY Times");
				preloadFavorite("trump@gmail.com", "www.washingtonpost.com", "Washington Post");
				
				preloadFavorite("obama@gmail.com", "www.facebook.com", "Facebook");
				preloadFavorite("obama@gmail.com", "www.twitter.com", "Twitter");
				preloadFavorite("obama@gmail.com", "www.ebay.com", "eBay");
				preloadFavorite("obama@gmail.com", "www.amazon.com", "Amazon");
			}			
		} catch (RollbackException e) {
			throw new ServletException(e);
		}
	}
	// Method to preload users.
	public void preloadUser(String emailAddress, String firstName, String lastName, String password)
			throws RollbackException {
		try {
			Transaction.begin();
	        UserBean user = new UserBean();
	        user.setEmailAddress(emailAddress);
	        user.setFirstName(firstName);
	        user.setLastName(lastName);
	        user.setPassword(password);
	    	userDAO.create(user);
	    	Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	// Method to preload favorites.
	public void preloadFavorite(String emailAddress, String url, String comment)
			throws RollbackException {
		try {
			Transaction.begin();
	        FavoriteBean favorite = new FavoriteBean();
	        favorite.setUrl(url);
	        favorite.setComment(comment);
	        favorite.setClickCount(0);
	        UserBean user = userDAO.read(emailAddress);
	        favorite.setUserId(user.getUserId());
	        favoriteDAO.create(favorite);
	        Transaction.commit();
		} finally {
            if (Transaction.isActive()) Transaction.rollback();
        } 
	}
	public FavoriteDAO getFavoriteDAO() { return favoriteDAO; }
	public UserDAO  getUserDAO()  { return userDAO;  }
}