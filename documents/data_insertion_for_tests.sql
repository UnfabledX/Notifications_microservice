
INSERT INTO notification_details VALUES     (1, 'POST_COMMENTED', 100, 200, null);
INSERT INTO notifications VALUES  (1, 2, 11, 1, now(), null);

INSERT INTO notification_details VALUES     (2, 'POST_COMMENTED', 100, 201, null);
INSERT INTO notifications VALUES  (2, 2, 12, 2, '2023-01-12 13:47:36', null);

INSERT INTO notification_details VALUES     (3, 'POST_COMMENTED', 100, 202, null);
INSERT INTO notifications VALUES  (3, 4, 13, 3, '2022-04-12 13:47:36', null);

INSERT INTO notification_details VALUES     (4, 'COMMENT_REPLIED', null, 200, 10);
INSERT INTO notifications VALUES  (4, 3, 13, 4, '2022-06-23 13:47:36', null);

INSERT INTO notification_details VALUES     (5, 'COMMENT_REPLIED', null, 200, 11);
INSERT INTO notifications VALUES  (5, 4, 14, 5, '2022-08-23 16:49:30', null);

INSERT INTO notification_details VALUES     (6, 'COMMENT_REPLIED', null, 200, 12);
INSERT INTO notifications VALUES  (6, 4, 14, 6, '2022-08-23 16:50:30', null);

INSERT INTO notification_details VALUES     (7, 'COMMENT_REPLIED', null, 200, 13);
INSERT INTO notifications VALUES  (7, 4, 14, 7, '2022-08-23 16:54:30', null);

INSERT INTO notification_details VALUES     (8, 'COMMENT_REPLIED', null, 200, 14);
INSERT INTO notifications VALUES  (8, 4, 14, 8, '2022-08-23 16:55:30', null);

INSERT INTO notification_details VALUES     (9, 'COMMENT_REPLIED', null, 200, 15);
INSERT INTO notifications VALUES  (9, 4, 14, 9, '2022-08-23 17:49:30', null);

INSERT INTO notification_details VALUES     (11, 'COMMENT_REPLIED', null, 200, 15);
INSERT INTO notifications VALUES (11, 4, 20, 11, '2022-08-23 08:22:07', null);

INSERT INTO notification_details VALUES     (19, 'COMMENT_REPLIED', null, 200, 15),
                                            (20, 'COMMENT_REPLIED', null, 200, 15);
INSERT INTO notifications VALUES (19, 10, 8, 19, '2023-01-12 13:47:36', null),
                                 (20, 10, 11, 20, '2022-06-23 13:49:35', null);