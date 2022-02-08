package hello.login.domain.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String , Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response){

        // generate sessionid and store it
        String sessionID = UUID.randomUUID().toString();
        sessionStore.put(sessionID, value);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionID);
        response.addCookie(mySessionCookie);
    }

    public Object getSession(HttpServletRequest request)
    {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie == null)
        {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request)
    {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if(cookie != null)
        {
            sessionStore.remove(cookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies= request.getCookies();
        if(cookies == null)
        {
            return null;
        }
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME)).findAny().orElse(null);
    }
}
