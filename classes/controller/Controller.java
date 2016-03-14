/**
 * 08-672 Homework#4.
 * @author Yujie Cha (Andrew ID: yujiecha)
 * December 12, 2015
 */
package controller;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Model;
import databeans.UserBean;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        // Sleep one second for building connection before pre-load info.
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        // Pre-load users and favorites for newly created tables as necessary.
        model.preload();
        
        Action.add(new ChangePwdAction(model));
        Action.add(new ListAction(model));
        Action.add(new ClickAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new ManageAction(model));
        Action.add(new BrowseAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new DeleteAction(model));
        Action.add(new AddAction(model));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("user");
        String action = getActionName(servletPath);
        
        if (action.equals("register.do") || action.equals("login.do")) {
            return Action.perform(action, request);
        }
        // Present welcome page with some messages.
        if (user == null && !action.equals("list.do") && !action.equals("click.do")) {
        	return Action.perform("browse.do", request);
        }
        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }
    
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }
        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }
        // Redirect users to the favorite web-page.
        if (nextPage.endsWith(".go")) {
        	String url = nextPage.substring(0, nextPage.length()-3);
        	if (!url.startsWith("http://")) {
        		response.sendRedirect("http://" + url);
        	} else {
        		response.sendRedirect(url);
        	}
        	return;
        }
        // Dispatch to JSP page.
        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }
        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
