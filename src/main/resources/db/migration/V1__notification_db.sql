CREATE schema IF NOT EXISTS notification_db;
USE notification_db;

-- --------------------------------------------------------------
-- NOTIFICATIONS DETAILS
-- Notification details represent detailed information about specific type
-- of notification:
--      + PostCommented - details about post_id, comment_id
--      + CommentReplied - details about comment_id, reply_id
--      + UserRegistered -
--      + PasswordResetRequest -
--      + to be continued...
-- --------------------------------------------------------------

CREATE TABLE notification_details
(
    id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type         VARCHAR(60),
    post_id      BIGINT,
    comment_id   BIGINT,
    reply_id     BIGINT
) CHARACTER SET=utf8mb4;

-- --------------------------------------------------------------
-- NOTIFICATIONS
-- notifications that are present in the project have fields:
--      + id - unique auto-incremented identifier for notifications
--      + owner_id - user id who is notified by notification
--      + createdBy_id - user id who triggered notification
--      + details_id - id of details if any present
--      + createdAt - time when notification was created
--      + updatedAt - time when the notified entity was updated
-- --------------------------------------------------------------
CREATE TABLE notifications
(
-- 'generated always AS identity' means id is autoincrement field
    id                      BIGINT    NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
    owner_id                BIGINT    NOT NULL,
    createdBy_id            BIGINT,
-- details_id in notifications table is associated with id in notification_details table
-- details_id of notifications = id of notification_details
    details_id              BIGINT    NOT NULL,
    createdAt               TIMESTAMP NOT NULL,
    updatedAt               TIMESTAMP,

-- this declaration contains the foreign key constraint
    CONSTRAINT details_fk
    FOREIGN KEY (details_id) REFERENCES notification_details (id)
    ON DELETE CASCADE
) CHARACTER SET=utf8mb4;