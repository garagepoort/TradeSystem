package de.codingair.tradesystem.utils.database;

import de.codingair.codingapi.files.ConfigFile;
import de.codingair.tradesystem.TradeSystem;
import de.codingair.tradesystem.utils.database.migrations.mysql.MySQLConnection;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseUtil {

    private static DatabaseUtil instance;
    private ConfigFile file = TradeSystem.getInstance().getFileManager().getFile("Config");
    private FileConfiguration config = file.getConfig();
    private static final String MYSQL_STRING = "MYSQL";
    private static final String SQLITE_STRING = "SQLITE";

    private DatabaseType databaseType;

    public static DatabaseUtil database() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    private DatabaseUtil() {
        String databaseType = config.getString("TradeSystem.Database.Type", MYSQL_STRING);
        if (MYSQL_STRING.equalsIgnoreCase(databaseType)) {
            this.databaseType = DatabaseType.MYSQL;
        } else if (SQLITE_STRING.equalsIgnoreCase(databaseType)) {
            this.databaseType = DatabaseType.SQLITE;
        } else {
            throw new RuntimeException("Invalid database type configured: " + databaseType);
        }
    }

    public void init() {
        if (databaseType == DatabaseType.MYSQL) {
            MySQLConnection.getInstance().initDataSource();
        } else if (databaseType == DatabaseType.SQLITE) {
            //No initialization needed
        } else {
            throw new RuntimeException("No database configured");
        }
    }

    public DatabaseType getType() {
        return databaseType;
    }
}
