/* 
 * Leks13
 * GPL v3
 */
package ru.leks13.jabbertimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.jivesoftware.smack.XMPPException;

public class Timer {

    public static void start() throws FileNotFoundException, IOException, XMPPException, SQLException, Exception {

        long time = (System.currentTimeMillis() / 1000L);
        Sql.timer(time);

    }
}
