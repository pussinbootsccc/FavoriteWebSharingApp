/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package model;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import databeans.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {
    public FavoriteDAO(String tableName, ConnectionPool cp) throws DAOException {
        super(FavoriteBean.class, tableName, cp);
    }
    public FavoriteBean[] getItems() throws RollbackException {
        // Match method with no argument that returns all beans.
        FavoriteBean[] items = match();
        return items;
    }
    public FavoriteBean[] getUserFavorites(int userId) throws RollbackException {
        FavoriteBean[] favoriteArray = match(MatchArg.equals("userId", userId));
        return favoriteArray;
    }
    // Increase the click count when click URL and handle concurrency.
    public void clickPlusOne(int favoriteId) throws RollbackException {
    	try {
    		Transaction.begin();
    		FavoriteBean bean;
        	// original read method that takes primary key
    		bean = read(favoriteId);
    		if (bean != null) {
    			bean.setClickCount(bean.getClickCount()+1);
    			update(bean);
    		}
    		Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
    }
    // Delete one favorite web-site and handle concurrency.
    public void delete(int favoriteId, int userId) throws RollbackException {
        try {
            Transaction.begin();
            FavoriteBean favorite = read(favoriteId);
            if (favorite == null) {
                throw new RollbackException("Favorite website does not exist: id=" + favoriteId);
            }
            if (userId != favorite.getUserId()) {
                throw new RollbackException("Favorite website not owned by " + userId);
            }
            delete(favoriteId);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
