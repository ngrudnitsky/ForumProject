package by.epam.nickgrudnitsky.util;

import by.epam.nickgrudnitsky.exception.ParameterValidationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Naming?
public class HttpUtil {
    private HttpUtil() {
    }

    public static String getRequestParameter(HttpServletRequest req, String parameterName, String patter)
            throws ParameterValidationException {
        String parameter = req.getParameter(parameterName);
        if (parameter != null && parameter.matches(patter)) {
            return parameter;
        }
        throw new ParameterValidationException(
                String.format("Wrong value %s of %s parameter.", parameter, parameterName));
    }

    public static String getRequestParameter(HttpServletRequest req, String parameterName)
            throws ParameterValidationException {
        return getRequestParameter(req, parameterName, ".*");
    }

    public static boolean isRequestParameterPresented(HttpServletRequest req, String parameterName, String patter) {
        String parameter = req.getParameter(parameterName);
        return parameter != null && parameter.matches(patter);
    }

    public static boolean isMethodPost(HttpServletRequest req) {
        return req.getMethod().equalsIgnoreCase("POST");
    }

    public static boolean isMethodGet(HttpServletRequest req) {
        return req.getMethod().equalsIgnoreCase("GET");
    }

    public static boolean isUserLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }
        Object user = session.getAttribute("user");
        return user != null;
    }

    public static void setCookie(HttpServletResponse resp, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60);
        resp.addCookie(cookie);
    }

    public static void setSessionAttribute(HttpServletRequest req, String name, Object value) {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(10);
        session.setAttribute(name, value);
    }
}
