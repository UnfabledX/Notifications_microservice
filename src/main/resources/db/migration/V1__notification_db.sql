CREATE schema IF NOT EXISTS notification_db;
USE notification_db;

# -- these commands remove all tables from the database
# -- An error occurs if tables not exist in DB, so just ignore commands
-- --------------------------------------------------------------
-- NOTIFICATIONS TYPES
-- According to the project requirements there are several types
-- of notifications:
--      + paid subscription when user subscribes to another in order
--        to see exclusive content.
--      + follow - when the user follows another to track his news
--      + comment - when the user commented someones post.
--      + reply - when the user replied to someones comment.
--      + pass_reset - when the user receives confirmation of password reset.
--      + registration - when the user receives verification after the registration.
-- --------------------------------------------------------------

CREATE TABLE notifications_types
(
    id          SMALLINT    NOT NULL  PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    text_body   TEXT        NOT NULL

) CHARACTER SET=utf8mb4;

-- inserting existing notification types:
INSERT INTO notifications_types
VALUES  (1, 'Sponsor Subscription', '%s have just subscribed with %s sponsor subscription %s ago'),
        (2, 'Follow', '%s started following you %s ago'),
        (3, 'Comment', '%s left the comment "%s" on your post "%s" %s ago'),
        (4, 'Reply', '%s replied with "%s" on your comment "%s" %s ago.'),
        (5, 'Password reset', 'Hi! Please click the link below to reset your account password.\nPlease note, the above link %s is  only valid for %d minutes'),
        (6, 'Registration', 'You just registered your account with the email: %s \nOne last step, just click here %s to verify your account.\nPlease note, the above link is only valid for %d minutes');

-- --------------------------------------------------------------
-- NOTIFICATIONS
-- notifications that are present in the project have fields:
--      + id - unique auto-incremented identifier for notifications
--      + owner_user_id - user id who is notified by notification
--      + trigger_user_id - user id who triggered notification
--      + notify_type_id - notifications type id
--      + createdAt - time when notification was created
-- --------------------------------------------------------------
CREATE TABLE notifications
(
-- 'generated always AS identity' means id is autoincrement field
-- (from 1 up to Integer.MAX_VALUE with the step 1)
    id                      BIGINT    NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
    owner_id                INT       NOT NULL,
    triggerer_id            INT,
-- notify_type_id in notifications table is associated with id in notifications_types table
-- notify_type_id of notifications = id of notifications_types
    notification_type_id    SMALLINT  NOT NULL,
    createdAt               TIMESTAMP NOT NULL,

-- this declaration contains the foreign key constraint
    CONSTRAINT notification_types_fk
    FOREIGN KEY (notification_type_id) REFERENCES notifications_types (id)
) CHARACTER SET=utf8mb4;