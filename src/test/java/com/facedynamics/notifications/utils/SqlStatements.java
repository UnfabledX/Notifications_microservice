package com.facedynamics.notifications.utils;

public class SqlStatements {

    public static final String details1 = "INSERT INTO notification_db.notification_details VALUES " +
            "(19, 'COMMENT_REPLIED', null, 200, 15)," +
            "(20, 'COMMENT_REPLIED', null, 200, 15);";
    public static final String notific1 ="INSERT INTO notification_db.notifications VALUES " +
            "(DEFAULT, 10, 8, 19, '2023-01-12 13:47:36', null)," +
            "(DEFAULT, 10, 11, 20, '2022-06-23 13:47:36', null)";

    public static final String details2 = "INSERT INTO notification_db.notification_details VALUES " +
            "(11, 'COMMENT_REPLIED', null, 200, 15);";
    public static final String notific2 = "INSERT INTO notification_db.notifications VALUES " +
            "(11, 4, 20, 11, '2022-08-23 08:22:07', null)";
}
