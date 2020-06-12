package by.epam.nickgrudnitsky.util;

import java.sql.SQLException;

public class ResetDataBase {
    public static void main(String[] args) throws SQLException {
        JdbcConnection.reset();
    }
}
