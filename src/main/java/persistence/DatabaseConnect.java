package persistence;

import domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseConnect {
    private static List<Student> lst = new ArrayList<>();
    private static Connection connection;
    private static String user = "admin";
    private static String password = "admin";

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/resources/StudentsJDBC", user, password);

        Statement statement = connection.createStatement();

        statement.executeUpdate("drop table if exists students");
        statement.executeUpdate("create table students (\n" +
                "    id identity, \n" +
                "    last_name varchar(255),\n" +
                "    first_name varchar(255),\n" +
                "    gender varchar(30),\n" +
                "    student_number int,\n" +
                "    class varchar(5),\n" +
                "    primary key (id),\n" +
                "    unique (class, student_number)\n" +
                ");");



        ResultSet rs = statement.executeQuery("SELECT * FROM students");

        while (rs.next()) {
            for (int i = 0; i< 6; i++)
            System.out.println(rs.getMetaData().getColumnName(i));
        }




        connection.close();
    }
}
