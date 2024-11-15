/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.sql.*;

public class BDrecord {

    public static Connection record;
    public static Statement stab;
    public static ResultSet result;

    public static void BDrecord() throws ClassNotFoundException, SQLException {
        record = null;
        Class.forName("org.sqlite.JDBC");
        record = DriverManager.getConnection("jdbc:sqlite:dataRec.s3db");
        System.out.println("БД подключена!");
    }

    public static void newTable() throws ClassNotFoundException, SQLException {
        stab = record.createStatement();
        stab.execute("CREATE TABLE if not exists 'record' ('no' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'click' text, 'time' text);");
        System.out.println("Таблица рекордов существует");
    }

    public static void ResetDB() throws SQLException {
        stab.executeUpdate("Delete from 'record'");
        stab.executeUpdate("DELETE FROM SQLITE_SEQUENCE WHERE name='record'");
    }

    public static void WriteDB() throws SQLException {
//        result.getInt("no");
        stab.execute("INSERT INTO 'record' ('name','click','time') VALUES ('Ivan', '160', '60'); ");
        stab.execute("INSERT INTO 'record' ('name','click','time') VALUES ('Anna', '158','60'); ");
        stab.execute("INSERT INTO 'record' ('name','click','time') VALUES ('Igor', '170','60'); ");
        System.out.println("Таблица заполнена");
    }

    public static void ReWriteDB(String[][] data) throws SQLException {
        for (int i = 0; i < 10; i++) {            
            String query = "INSERT INTO 'record'('name','click','time') VALUES('" + data[i][1] + "','" +  data[i][2] + "','" + data[i][3] + "')";
            stab.execute(query);
        }
        System.out.println("Таблица заполнена");
    }

    public static void ReadDB() throws ClassNotFoundException, SQLException {
        result = stab.executeQuery("SELECT * FROM record");
        while (result.next()) {
            int no = result.getInt("no");
            String name = result.getString("name");
            String click = result.getString("click");
            String time = result.getString("time");
            System.out.println("no = " + no);
            System.out.println("name = " + name);
            System.out.println("click = " + click);
            System.out.println("time = " + time);
            System.out.println();
        }
        System.out.println("Таблица выведена");
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        record.close();
        stab.close();
        result.close();
        System.out.println("Соединения закрыты");
    }

}
