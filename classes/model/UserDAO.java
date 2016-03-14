/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package model;
import java.util.Arrays;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import databeans.UserBean;

public class UserDAO extends GenericDAO<UserBean> {

    public UserDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(UserBean.class, tableName, pool);
    }
    public UserBean[] getUsers() throws RollbackException {
        UserBean[] users = match();
        Arrays.sort(users); // We want them sorted by last and first names (as per User.compareTo());
        return users;
    }
	// Overload the read method to take emailAddress as parameter(which is not primary key).
    // Define the read method to fetch beans by given email address.
	public UserBean read(String emailAddress) throws RollbackException {
        UserBean[] userArray;
		userArray = match(MatchArg.equals("emailAddress", emailAddress));
        if(userArray.length != 0) {
        	// The given email matched in database, return that certain bean.
        	return userArray[0];
        } else {
			// The given email not matched in database, bean is null.
			return null;
        }
	}

    public void setPassword(String emailAddress, String password) throws RollbackException {
        try {
            Transaction.begin();
            // Fetch the user bean in database.
            UserBean dbUser = read(emailAddress);
            if (dbUser == null) {
                throw new RollbackException("User " + emailAddress + " no longer exists");
            }
            // Reset password with input new password.
            dbUser.setPassword(password);
            // Update the database.
            update(dbUser);
            Transaction.commit();
        } finally {
            if (Transaction.isActive())
            	Transaction.rollback();
        }
    }
}
