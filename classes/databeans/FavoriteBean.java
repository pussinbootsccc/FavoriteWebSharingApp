/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package databeans;
import org.genericdao.PrimaryKey;

@PrimaryKey("favoriteId")
public class FavoriteBean {
	private int favoriteId;
	private int userId;
	private String url;
	private String comment;
	private int clickCount;

	public int getFavoriteId() { return favoriteId;}
	public int getUserId()     { return userId; }
	public String getUrl()     { return url; }
	public String getComment() { return comment; }
	public int getClickCount() { return clickCount; }

	public void setFavoriteId(int _favoriteId) { favoriteId = _favoriteId; }
	public void setUserId(int _userId)         { userId = _userId; }
	public void setUrl(String _url)            { url = _url; }
	public void setComment(String _comment)    { comment = _comment; }
	public void setClickCount(int _clickCount) { clickCount = _clickCount; }
}
