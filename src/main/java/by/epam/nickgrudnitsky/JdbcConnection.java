package by.epam.nickgrudnitsky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void reset() throws SQLException {
        try (Connection connection = JdbcConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP SCHEMA IF EXISTS `mydb` ;");
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `firstName` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `userName` VARCHAR(45) NULL,\n" +
                    "  `email` VARCHAR(45) NULL,\n" +
                    "  `password` VARCHAR(100) NULL,\n" +
                    "  `status` VARCHAR(10) NULL,\n" +
                    "  `createdAt` DATETIME NULL,\n" +
                    "  `updatedAt` DATETIME NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`roles` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `status` VARCHAR(45) NULL,\n" +
                    "  `createdAt` DATETIME NULL,\n" +
                    "  `updatedAt` DATETIME NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`posts` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `title` VARCHAR(45) NULL,\n" +
                    "  `content` MEDIUMTEXT NULL,\n" +
                    "  `status` VARCHAR(45) NULL,\n" +
                    "  `createdAt` DATETIME NULL,\n" +
                    "  `updatedAt` DATETIME NULL,\n" +
                    "  `users_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`, `users_id`),\n" +
                    "  INDEX `fk_posts_users1_idx` (`users_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_posts_users1`\n" +
                    "    FOREIGN KEY (`users_id`)\n" +
                    "    REFERENCES `mydb`.`users` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`comments` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `content` MEDIUMTEXT NULL,\n" +
                    "  `status` VARCHAR(45) NULL,\n" +
                    "  `updatedAT` DATETIME NULL,\n" +
                    "  `createdAt` DATETIME NULL,\n" +
                    "  `users_id` INT NOT NULL,\n" +
                    "  `posts_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`, `users_id`, `posts_id`),\n" +
                    "  INDEX `fk_comments_users1_idx` (`users_id` ASC) VISIBLE,\n" +
                    "  INDEX `fk_comments_posts1_idx` (`posts_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_comments_users1`\n" +
                    "    FOREIGN KEY (`users_id`)\n" +
                    "    REFERENCES `mydb`.`users` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `fk_comments_posts1`\n" +
                    "    FOREIGN KEY (`posts_id`)\n" +
                    "    REFERENCES `mydb`.`posts` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`users_has_roles` (\n" +
                    "  `users_id` INT NOT NULL,\n" +
                    "  `roles_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`users_id`, `roles_id`),\n" +
                    "  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC) VISIBLE,\n" +
                    "  INDEX `fk_users_has_roles_users_idx` (`users_id` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_users_has_roles_users`\n" +
                    "    FOREIGN KEY (`users_id`)\n" +
                    "    REFERENCES `mydb`.`users` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `fk_users_has_roles_roles1`\n" +
                    "    FOREIGN KEY (`roles_id`)\n" +
                    "    REFERENCES `mydb`.`roles` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;");
            statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`) VALUES ('USER', 'ACTIVE');");
            statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`) VALUES ('ADMIN', 'ACTIVE');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Nikita', 'Grudnitsky', 'NickGS', 'grudnitsky@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '2');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Randy', 'Marsh', 'Stone', 'randyy@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('2', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Bob', 'Kelso', 'Doc', 'scrubs@mail.ru', '123', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
            statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('3', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`posts` (`title`, `content`, `status`, `users_id`) VALUES ('Welcome', 'This is the first post', 'ACTIVE', '1');");
            statement.executeUpdate("INSERT INTO `mydb`.`comments` (`content`, `status`, `users_id`, `posts_id`) VALUES ('This is first comment', 'ACTIVE', '1', '1');");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }
        return connection;
    }
}
