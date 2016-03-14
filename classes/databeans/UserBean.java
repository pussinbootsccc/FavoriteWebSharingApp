/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package databeans;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("userId")
public class UserBean implements Comparable<UserBean> {
	private int userId;
	private String  emailAddress = null;
	private String  hashedPassword = "*";
	private int     salt           = 0;
	private String  firstName      = null;
	private String  lastName       = null;

	public boolean checkPassword(String password) {
		return hashedPassword.equals(hash(password));
	}
	// Order first by lastName and then by firstName
	public int compareTo(UserBean other) {
		int c = lastName.compareTo(other.lastName);
		if (c != 0) 
			return c;
		c = firstName.compareTo(other.firstName);
		if (c != 0) 
			return c;
		return emailAddress.compareTo(other.emailAddress);
	}
	public boolean equals(Object obj) {
		if (obj instanceof UserBean) {
			UserBean other = (UserBean) obj;
			return emailAddress.equals(other.emailAddress);
		}
		return false;
	}
	public int     getUserId()         { return userId; }
	public String  getHashedPassword() { return hashedPassword; }
	public String  getEmailAddress()   { return emailAddress; }
	public int     getSalt()           { return salt;           }
	public String  getFirstName()      { return firstName;      }
	public String  getLastName()       { return lastName;       }
	public int     hashCode()          { return emailAddress.hashCode(); }
	
	public void setEmailAddress(String _emailAddress) { emailAddress = _emailAddress; }
	public void setUserId(int _userId)       { userId = _userId; }
	public void setHashedPassword(String x)  { hashedPassword = x; }
	public void setPassword(String s)        { salt = newSalt(); hashedPassword = hash(s); }
	public void setSalt(int x)               { salt = x;           }
	public void setFirstName(String s)       { firstName = s;      }
	public void setLastName(String s)        { lastName = s;       }

	public String toString() {
		return "User("+getEmailAddress()+")";
	}
	private String hash(String clearPassword) {
		if (salt == 0) return null;
		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();
		return digestStr;
	}
	
	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}
}
