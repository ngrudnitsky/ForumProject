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

    public static Connection getConnection() {
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

    public static void main(String[] args) throws SQLException {
        JdbcConnection.reset();
    }

    public static void reset() throws SQLException {
        try (Connection connection = JdbcConnection.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP SCHEMA IF EXISTS `mydb` ;");
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;");
            createUsersTable(statement);
            createRolesTable(statement);
            createUsersHasRolesTable(statement);
            createPostsTable(statement);
            createCommentsTable(statement);
            fillInRoles(statement);
            fillInUsers(statement);
            fillInPosts(statement);
            fillInComments(statement);
        }
    }

    private static void fillInComments(Statement statement) throws SQLException {
        statement.executeUpdate("INSERT INTO `mydb`.`comments` (`content`, `status`, `users_id`, `posts_id`, `createdAt`, `updatedAt`) VALUES ('This is first comment', 'ACTIVE', '1', '1', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
    }

    private static void fillInPosts(Statement statement) throws SQLException {
        statement.executeUpdate("INSERT INTO `mydb`.`posts` (`title`, `preview`, `content`, `status`, `users_id`, `createdAt`, `updatedAt`) VALUES ('Нейросеть «возвращает» чёткость пиксельным лицам Думгая, Марио и Крипера из Minecraft', 'Иногда работает хорошо, но чаще получается жутко.', 'Автор телеграм-канала про искусство и машинное обучение MLArt привлёк внимание к программе Face Depixelizer (GitHub). Нейросеть принимает изображение лица в низком разрешении, подбирает фотографию, которая при пикселизации даст наиболее подходящий результат, и подгоняет её под пропорции исходного файла.\n" +
                "\n" +
                "Достоверно «восстанавливать» фотографии таким методом скорее всего не выйдет. Но зато лица героев старых игр прогонять через нейронку оказалось очень весело. Пользователи Twitter очень быстро сгенерировали целую галерею портретов.', 'ACTIVE', '1', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
    }

    private static void fillInUsers(Statement statement) throws SQLException {
        statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Nikita', 'Grudnitsky', 'NickGS', 'grudnitsky@mail.ru', 'testPassword1', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
        statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '1');");
        statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('1', '2');");
        statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Randy', 'Marsh', 'Stone', 'randyy@mail.ru', 'testPassword1', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
        statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('2', '1');");
        statement.executeUpdate("INSERT INTO `mydb`.`users` (`firstName`, `lastName`, `userName`, `email`, `password`, `status`, `createdAt`, `updatedAt`) VALUES ('Bob', 'Kelso', 'Doc', 'scrubs@mail.ru', 'testPassword1', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
        statement.executeUpdate("INSERT INTO `mydb`.`users_has_roles` (`users_id`, `roles_id`) VALUES ('3', '1');");
    }

    private static void fillInRoles(Statement statement) throws SQLException {
        statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`, `createdAt`, `updatedAt`) VALUES ('USER', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
        statement.executeUpdate("INSERT INTO `mydb`.`roles` (`name`, `status`, `createdAt`, `updatedAt`) VALUES ('ADMIN', 'ACTIVE', '2014-03-16 00:00:00.000', '2014-03-16 00:00:00.000');");
    }

    private static void createCommentsTable(Statement statement) throws SQLException {
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
    }

    private static void createPostsTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`posts` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `title` VARCHAR(200) NOT NULL,\n"
                + "  `preview` VARCHAR(200) NOT NULL,\n"
                + "  `content` MEDIUMTEXT NOT NULL,\n"
                + "  `status` VARCHAR(10) NOT NULL,\n"
                + "  `updatedAt` DATETIME NOT NULL,\n"
                + "  `createdAt` DATETIME NOT NULL,\n"
                + "  `users_id` INT NOT NULL,\n"
                + "  PRIMARY KEY (`id`, `users_id`))\n"
                + "ENGINE = InnoDB;");
    }

    private static void createUsersHasRolesTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`users_has_roles` (\n"
                + "  `users_id` INT NOT NULL,\n"
                + "  `roles_id` INT NOT NULL,\n"
                + "  PRIMARY KEY (`users_id`, `roles_id`))\n"
                + "ENGINE = InnoDB;");
    }

    private static void createRolesTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mydb`.`roles` (\n"
                + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "  `name` VARCHAR(45) NOT NULL,\n"
                + "  `status` VARCHAR(10) NOT NULL,\n"
                + "  `updatedAt` DATETIME NOT NULL,\n"
                + "  `createdAt` DATETIME NOT NULL,\n"
                + "  PRIMARY KEY (`id`))\n"
                + "ENGINE = InnoDB;");
    }

    private static void createUsersTable(Statement statement) throws SQLException {
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
    }
}
