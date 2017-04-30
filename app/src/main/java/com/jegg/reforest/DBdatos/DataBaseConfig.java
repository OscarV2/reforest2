package com.jegg.reforest.DBdatos;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by oscarvc on 29/04/17.
 */

public class DataBaseConfig extends OrmLiteConfigUtil {

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormliteconfig.txt");
    }

}
