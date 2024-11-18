package edu.student_orden.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBStartUp {
    public static void startUp() throws URISyntaxException, IOException, SQLException {
        URL url1 = DictionaryDaoImpl.class.getClassLoader().getResource("student_project.sql");
        List<String> stringList = Files.readAllLines(Paths.get(url1.toURI()));
        URL url2 = DictionaryDaoImpl.class.getClassLoader().getResource("student_data.sql");
        List<String> stringList2 = Files.readAllLines(Paths.get(url2.toURI()));

        String sql = stringList.stream().collect(Collectors.joining());
        String sql2 = stringList2.stream().collect(Collectors.joining());


        try (Connection connection = DictionaryDaoImplConnection.getConn();
             Statement smtp = connection.createStatement()) {
            smtp.executeUpdate(sql);
            smtp.executeUpdate(sql2);
        }
        System.out.println("SQL TEST Finished");
    }
}
