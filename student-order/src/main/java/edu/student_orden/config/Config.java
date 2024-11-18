package edu.student_orden.config;

import java.io.InputStream;
import java.util.Properties;

public class Config
{
    public static final String  DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name) {
        if (properties.isEmpty()) {
//            properties.put("db.url" , "jdbc:postgresql://localhost:5432/jc_student");
//            properties.put("db.login", "postgres");
//            properties.put("db.password", "postgre");

            try (InputStream is = Config.class.getClassLoader()
                    .getResourceAsStream("dao.properties")) {
                System.out.println("is :" + is);
                properties.load(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(name);
    }

}
