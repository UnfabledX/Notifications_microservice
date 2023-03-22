CREATE schema IF NOT EXISTS notification_db;

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

CREATE TABLE IF NOT EXISTS notification_details
(
    id           bigserial,
    type         character varying,
    name         character varying,
    email        character varying,
    post_id      bigint,
    comment_id   bigint,
    reply_id     bigint
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
CREATE TABLE IF NOT EXISTS notifications
(
-- 'generated always AS identity' means id is autoincrement field
    id                      bigserial,
    owner_id                bigint    NOT NULL,
    createdBy_id            bigint,
-- details_id in notifications table is associated with id in notification_details table
-- details_id of notifications = id of notification_details
    details_id              bigint    NOT NULL,
    notification_createdAt  TIMESTAMP NOT NULL,
    entity_createdAt        TIMESTAMP NOT NULL ,

-- this declaration contains the foreign key constraint
    CONSTRAINT details_fk
    FOREIGN KEY (details_id) REFERENCES notification_details (id)
    ON DELETE CASCADE
);