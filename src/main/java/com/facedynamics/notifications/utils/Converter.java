package com.facedynamics.notifications.utils;

import com.facedynamics.notifications.model.Notification;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

public class Converter {

    public static Map<String, Object> getResponse(Page<Notification> list) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("notifications", list.getContent());
        response.put("currentPage", list.getNumber());
        response.put("notificationsPerPage", list.getSize());
        response.put("totalNotifications", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }
}
