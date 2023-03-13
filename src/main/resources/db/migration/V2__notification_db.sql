ALTER TABLE notifications
    DROP FOREIGN KEY notification_types_fk;
DROP TABLE IF EXISTS notification_types;
ALTER TABLE notifications
    ADD COLUMN updatedAt TIMESTAMP
        AFTER createdAt;
