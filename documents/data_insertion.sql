DELETE FROM notification_db.notifications;
INSERT INTO notifications
VALUES (DEFAULT, 4, 11, 3, now()),
       (DEFAULT, 3, 8, 4, '2023-01-12 13:47:36'),
       (DEFAULT, 4, 10, 2, '2022-04-12 13:47:36'),
       (DEFAULT, 3, 10, 1, '2022-06-23 13:47:36'),
       (DEFAULT, 4, null, 5, '2022-08-23 16:49:30'),
       (DEFAULT, 4, 21, 1, '2022-08-23 16:49:30'),
       (DEFAULT, 4, 20, 1, '2022-08-23 08:22:07'),
       (DEFAULT, 4, 15, 1, '2022-08-24 06:21:44'),
       (DEFAULT, 2, null, 6, '2023-02-05 21:53:35'),
       (DEFAULT, 2, null, 5, '2023-02-05 21:53:35');