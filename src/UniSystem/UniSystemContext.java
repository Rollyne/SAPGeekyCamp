package UniSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UniSystemContext {

    private Connection connection;

    public UniSystemContext() throws SQLException {
        createDatabaseIfNotExists();
    }

    public Connection getConnection(){
        return this.connection;
    }


    private void createDatabaseIfNotExists() throws SQLException {
        try {
            connectToDatabase(DatabaseSettings.dbName, DatabaseSettings.port, DatabaseSettings.username, DatabaseSettings.password);
        } catch (SQLException e) {
            connectToDatabase("postgres", DatabaseSettings.port, DatabaseSettings.username, DatabaseSettings.password);
            createSchema();
        }
    }

    private void connectToDatabase(String dbName, String port, String username, String password) throws SQLException {
        String url = String.format("jdbc:postgresql://localhost:%s/%s", port, dbName.toLowerCase());
         this.connection = DriverManager.getConnection(url, username, password);
    }

    private void createSchema() throws SQLException {
        Statement cmd = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE UniSystem; ");

        cmd.execute(sql.toString());

        connectToDatabase(DatabaseSettings.dbName, DatabaseSettings.port, DatabaseSettings.username, DatabaseSettings.password);
        sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS Faculties ")
                .append("(id SERIAL PRIMARY KEY, ")
                .append("name varchar(255) NOT NULL); ")

                .append("CREATE TABLE IF NOT EXISTS Students ")
                .append("(id SERIAL PRIMARY KEY,")
                .append("firstName varchar(255) NOT NULL,")
                .append( "lastName varchar(255) NOT NULL,")
                .append("facultyId int REFERENCES Faculties (id) ON DELETE CASCADE); ")

                .append("CREATE TABLE IF NOT EXISTS Courses ")
                .append("(id SERIAL PRIMARY KEY,")
                .append("name varchar(255) NOT NULL, ")
                .append("description varchar(1000),")
                .append("credits INTEGER NOT NULL); ")

                .append("CREATE TABLE IF NOT EXISTS Students_Courses ")
                .append("(studentId INTEGER NOT NULL REFERENCES Students (id) ON DELETE CASCADE, ")
                .append("courseId INTEGER NOT NULL REFERENCES Courses (id) ON DELETE CASCADE); ");
        System.out.println(sql.toString());
        //cmd.execute(sql.toString());
    }
}
