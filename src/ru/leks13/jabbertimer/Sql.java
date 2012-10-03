/* 
 * Leks13
 * GPL v3
 */
package ru.leks13.jabbertimer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.jivesoftware.smack.XMPPException;

public class Sql {

    public static ResultSet timer(Long time) throws ClassNotFoundException, SQLException, XMPPException {
        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:timer.db");
        ResultSet rs;
        int i = 1;
        try (java.sql.Statement st = bd.createStatement()) {
            st.execute("create table if not exists 'TABLE1' ('time' long, 'jid' text, 'id' int, 'note' text);");
            rs = st.executeQuery("SELECT * FROM TABLE1 WHERE time = " + time + ";");
            while (rs.next()) {
                long j = rs.getLong("id");
                String jid = rs.getString("jid");
                String msg = rs.getString("note");
                XmppNet.sendMessage(jid, msg);
                boolean f = st.execute("DELETE FROM TABLE1 WHERE id=" + j + ";");
                st.close();
                i++;
            }
            st.close();
        }

        return rs;
    }

    public static boolean nodataFromBase(String s) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:timer.db");
        boolean f;
        try (java.sql.Statement st = bd.createStatement()) {
            st.execute("create table if not exists 'TABLE1' ('time' long, 'jid' text, 'id' int, 'note' text);");
            f = st.execute(s);
            st.close();
        }

        return f;
    }

    public static String listOfTimer(String jid) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        ResultSet rs;
        Connection bd = DriverManager.getConnection("jdbc:sqlite:timer.db");
        String f = "";
        jid = "'" + jid + "'";
        java.util.Date time;
        try (java.sql.Statement st = bd.createStatement()) {
            st.execute("create table if not exists 'TABLE1' ('time' long, 'jid' text, 'id' int, 'note' text);");
            rs = st.executeQuery("SELECT * FROM TABLE1 WHERE jid = " + jid + " AND time>0;");
            while (rs.next()) {
                String timeBase = rs.getString("time");
                String S = new SimpleDateFormat("dd.MM.yyyy HH:mm Z").format(Long.valueOf(timeBase) * 1000);
                f += S + "\n";
            }
            st.close();
        }

        return f;
    }

    public static String listOfNote(String jid) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        ResultSet rs;
        Connection bd = DriverManager.getConnection("jdbc:sqlite:timer.db");
        String f = "";
        jid = "'" + jid + "'";
        java.util.Date time;
        try (java.sql.Statement st = bd.createStatement()) {
            st.execute("create table if not exists 'TABLE1' ('time' long, 'jid' text, 'id' int, 'note' text);");
            rs = st.executeQuery("SELECT * FROM TABLE1 WHERE jid = " + jid + " AND time=0;");
            while (rs.next()) {
                String note = rs.getString("note");
                String id = rs.getString("id");
                f += "#" +id + "\n" + note + "\n \n";
            }
            st.close();
        }

        return f;
    }

    public static void deleteNote(String jid, String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        ResultSet rs;
        Connection bd = DriverManager.getConnection("jdbc:sqlite:timer.db");
        String f = "";
        jid = "'" + jid + "'";
        boolean res;
        try (java.sql.Statement st = bd.createStatement()) {
            st.execute("create table if not exists 'TABLE1' ('time' long, 'jid' text, 'id' int, 'note' text);");
                res = st.execute("DELETE FROM TABLE1 WHERE id=" + id + " AND jid="+jid + ";");
            st.close();
        }
    }
}
