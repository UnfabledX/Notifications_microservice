package com.facedynamics.notifications;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.controllers.NotificationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationsApplicationTests extends BaseTest {

	@Autowired
	private NotificationController controller;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(controller);
	}
}