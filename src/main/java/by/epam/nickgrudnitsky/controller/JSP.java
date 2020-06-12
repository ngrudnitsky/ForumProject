package by.epam.nickgrudnitsky.controller;

public enum JSP {
    PROFILE("/profile.jsp"),
    SIGN_UP("/signup.jsp"),
    INDEX("/index.jsp"),
    ERROR("/error.jsp");

    private String jspAddress;

    JSP(String jspAddress) {
        this.jspAddress = jspAddress;
    }

    public String getJspAddress() {
        return jspAddress;
    }
}
