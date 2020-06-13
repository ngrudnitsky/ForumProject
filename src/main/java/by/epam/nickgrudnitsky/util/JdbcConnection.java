package by.epam.nickgrudnitsky.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
    public static final String URL = "jdbc:mysql://localhost:8889/mydb?serverTimezone=UTC";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    private static Connection connection;

    static void reset() throws SQLException {
        try (Connection connection = JdbcConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP SCHEMA IF EXISTS `mydb` ;");
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`users` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `firstName` VARCHAR(45) NOT NULL,\n"
                    + "  `lastName` VARCHAR(45) NOT NULL,\n"
                    + "  `userName` VARCHAR(45) NOT NULL,\n"
                    + "  `email` VARCHAR(45) NOT NULL,\n"
                    + "  `password` VARCHAR(100) NOT NULL,\n"
                    + "  `status` VARCHAR(10) NOT NULL,\n"
                    + "  `updatedAt` DATETIME NOT NULL,\n"
                    + "  `createdAt` DATETIME NOT NULL,\n"
                    + "  PRIMARY KEY (`id`))\n"
                    + "ENGINE = InnoDB;\n");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`roles` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `name` VARCHAR(45) NOT NULL,\n"
                    + "  `status` VARCHAR(10) NOT NULL,\n"
                    + "  `updatedAt` DATETIME NOT NULL,\n"
                    + "  `createdAt` DATETIME NOT NULL,\n"
                    + "  PRIMARY KEY (`id`))\n"
                    + "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`users_has_roles` (\n"
                    + "  `users_id` INT NOT NULL,\n"
                    + "  `roles_id` INT NOT NULL,\n"
                    + "  PRIMARY KEY (`users_id`, `roles_id`))\n"
                    + "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`posts` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `title` VARCHAR(45) NOT NULL,\n"
                    + "  `content` MEDIUMTEXT NOT NULL,\n"
                    + "  `status` VARCHAR(10) NOT NULL,\n"
                    + "  `updatedAt` DATETIME NOT NULL,\n"
                    + "  `createdAt` DATETIME NOT NULL,\n"
                    + "  `users_id` INT NOT NULL,\n"
                    + "  PRIMARY KEY (`id`, `users_id`))\n"
                    + "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`comments` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `content` MEDIUMTEXT NOT NULL,\n"
                    + "  `status` VARCHAR(10) NOT NULL,\n"
                    + "  `updatedAt` DATETIME NOT NULL,\n"
                    + "  `createdAt` DATETIME NOT NULL,\n"
                    + "  `users_id` INT NOT NULL,\n"
                    + "  `posts_id` INT NOT NULL,\n"
                    + "  PRIMARY KEY (`id`, `users_id`, `posts_id`))\n"
                    + "ENGINE = InnoDB;");
            statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`, `createdAt`, `updatedAt`) VALUES ('USER', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`, `createdAt`, `updatedAt`) VALUES ('ADMIN', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Nikita', 'Grudnitsky', 'NickGS', 'grudnitsky@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '2');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Randy', 'Marsh', 'Stone', 'randyy@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('2', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Bob', 'Kelso', 'Doc', 'scrubs@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('3', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`posts` (`title`, `content`, `status`, `users_id`, `createdAt`, `updatedAt`) VALUES ('Welcome', 'This is the first post', 'ACTIVE', '1', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`comments` (`content`, `status`, `users_id`, `posts_id`, `createdAt`, `updatedAt`) VALUES ('This is first comment', 'ACTIVE', '1', '1', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
        }
    }

    public static Connection getConnection()  {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            }
        } catch (SQLException e) {
            /// TODO: 6/6/20
            e.printStackTrace();
        }
        return connection;
    }
}
