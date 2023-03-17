package com.facedynamics.notifications.utils;

public class SqlStatements {

    public static final String details1 = "INSERT INTO notification_db.notification_details VALUES " +
            "(100, 'COMMENT_REPLIED', null, 200, 15)," +
            "(101, 'COMMENT_REPLIED', null, 200, 15);";
    public static final String notific1 ="INSERT INTO notification_db.notifications VALUES " +
            "(100, 10, 8, 100, '2023-01-12 13:47:36', null)," +
            "(101, 10, 11, 101, '2022-06-23 13:47:36', null)";

    public static final String details2 = "INSERT INTO notification_db.notification_details VALUES " +
            "(102, 'COMMENT_REPLIED', null, 200, 15);";
    public static final String notific2 = "INSERT INTO notification_db.notifications VALUES " +
            "(102, 4, 20, 102, '2022-08-23 08:22:07', null)";
}
