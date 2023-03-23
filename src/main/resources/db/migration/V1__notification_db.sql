CREATE schema IF NOT EXISTS notifications_db;

-- --------------------------------------------------------------
-- NOTIFICATIONS DETAILS
-- Notification details represent detailed information about specific type
-- of notification:
--      + PostCommented - details about post_id, comment_id
--      + CommentReplied - details about comment_id, reply_id
--      + UserRegistered - name and email
--      + PasswordResetRequest - name and email
--      + to be continued...
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS notifications_db.notification_details
(
    details_id       bigserial,
    type             character varying NOT NULL,
    name             character varying,
    email            character varying,
    post_id          bigint,
    comment_id       bigint,
    reply_id         bigint,
    entity_createdAt timestamp         NOT NULL,
    PRIMARY KEY (details_id)
);

-- --------------------------------------------------------------
-- NOTIFICATIONS
-- notifications that are present in the project have fields:
--      + id - unique auto-incremented identifier for notifications
--      + owner_id - user id who is notified by notification
--      + createdBy_id - user id who triggered notification
--      + details_id - id of details if any present
--      + createdAt - time when notification was created
--      + updatedAt - time when the entity was created
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS notifications_db.notifications
(
-- 'generated always AS identity' means id is autoincrement field
    id                     bigserial,
    owner_id               bigint        NOT NULL,
    createdBy_id           bigint,
-- details_id in notifications table is associated with id in notification_details table
-- details_id of notifications = id of notification_details
    details_id             bigint UNIQUE NOT NULL,
    notification_createdAt TIMESTAMP     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_details
        FOREIGN KEY(details_id)
            REFERENCES notifications_db.notification_details(details_id)
);