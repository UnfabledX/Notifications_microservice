DELETE
FROM notification_db.notifications;
INSERT INTO notifications
VALUES (DEFAULT, 4, 11, 3, now()),
       (DEFAULT, 3, 8, 6, '2023-01-12 13:47:36'),
       (DEFAULT, 4, 10, 6, '2022-04-12 12:36:36'),
       (DEFAULT, 4, 10, 4, '2022-04-12 12:36:36'),
       (DEFAULT, 3, 10, 5, '2021-06-23 13:47:36'),
       (DEFAULT, 4, null, 1, '2016-08-23 16:49:30'),
       (DEFAULT, 4, 21, 4, '2019-08-23 16:49:30'),
       (DEFAULT, 4, 20, 3, '2015-08-23 08:22:07'),
       (DEFAULT, 4, null, 2, '2020-08-24 06:21:44'),
       (DEFAULT, 4, 15, 1, '2017-08-24 06:21:44'),
       (DEFAULT, 2, null, 1, '2022-02-05 21:53:35'),
       (DEFAULT, 2, null, 2, '2023-02-05 21:53:35'),
       (DEFAULT, 1, null, 1, '2022-04-12 12:32:00'),
       (DEFAULT, 1, 10, 4, '2022-04-12 12:50:36'),
       (DEFAULT, 1, 10, 5, '2021-06-23 13:47:36');