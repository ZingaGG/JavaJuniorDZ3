// * * 0. Разобрать код с семниара
//   * 1. Повторить код с семниара без подглядываний на таблице Student с полями:
//   * 1.1 id - int
//   * 1.2 firstName - string
//   * 1.3 secondName - string
//   * 1.4 age - int
//   * 2.* Попробовать подключиться к другой БД


import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "3109";
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            acceptConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void acceptConnection(Connection connection){
        createTable(connection);

        insertAction(connection);

        selectAction(connection);

        deleteAction(connection);

        System.out.println();
        System.out.println("После удаления: ");
        System.out.println();

        selectAction(connection);

        uodateAction(connection);

        System.out.println();
        System.out.println("После обновления: ");
        System.out.println();

        selectAction(connection);
    }

    private static void uodateAction(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update student set secondName = ? where firstName = ?")) {
            preparedStatement.setString(1, "Updated");
            preparedStatement.setString(2, "fName1");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteAction(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Student where id = ?")) {
            preparedStatement.setInt(1, 5);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void selectAction(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, firstName, secondName, age from Student");

            while(resultSet.next()){
                long id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                int age = resultSet.getInt("age");

                System.out.println("id = " + id + ", firstName = " + firstName
                + ", secondName = "  + secondName
                + ", age = " + age);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertAction(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    insert into Student(id, firstName, secondName, age) values
                    (1, 'fName1', 'sName1', 20),
                    (2, 'fName2', 'sName2', 21),
                    (3, 'fName3', 'sName3', 22),
                    (4, 'fName4', 'sName4', 23),
                    (5, 'fName5', 'sName5', 24)
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()){
            statement.execute("""
                    drop table if exists Student;
                    create table Student (
                    id bigint,
                    firstName varchar(20),
                    secondName varchar(20),
                    age int
                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


