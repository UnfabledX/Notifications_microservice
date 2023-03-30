package com.facedynamics.notifications.events;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentDisliked;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommentDislikedEventListener {

    private final EmailService emailService;
    /**
     * Represents the map of postIds, which were liked and corresponding events
     * were triggered. Number of notifications in list equals to number of likes.
     */
    private static final Map<Long, List<CommentDislikeEvent>> listMap = new HashMap<>();
    private static Long currentTime = System.currentTimeMillis();

    @Value("${source.mail.islike.delaymillis}")
    private long delay;

    @EventListener(CommentDislikeEvent.class)
    public void onCommentDislikedEvent(CommentDislikeEvent event){
        if (event != null) {
            NotificationDto dto = event.getReceivedDTO();
            NotificationContent<CommentDisliked> content = dto.content();
            CommentDisliked commentDisliked = content.getChild();
            Long commentId = commentDisliked.getCommentId();
            if (listMap.containsKey(commentId)) {
                List<CommentDislikeEvent> listInside = listMap.get(commentId);
                listInside.add(event);
                listMap.put(commentId, listInside);
            } else {
                List<CommentDislikeEvent> list = new ArrayList<>();
                list.add(event);
                listMap.put(commentId, list);
            }
        }

        if (currentTime + delay < System.currentTimeMillis()) {
            if (!listMap.isEmpty()){
                for (Map.Entry<Long, List<CommentDislikeEvent>> entry: listMap.entrySet()){
                    List<CommentDislikeEvent> events = entry.getValue();
                    NotificationDto firstDto = events.get(0).getReceivedDTO();
                    NotificationUserServiceDTO firstUserServiceDTO = events.get(0).getOwnerDTO();
                    emailService.sendEmail(firstDto, firstUserServiceDTO, events);
                }
                listMap.clear();
            }
            currentTime = System.currentTimeMillis();
        }
    }

    /**
     * Invokes onCommentDislikedEvent() method
     * when cached map is not Empty but email notification
     * must be delivered.
     */
    @Scheduled(fixedDelay = 60000)
    public void fixedDelaySch() {
        onCommentDislikedEvent(null);
    }
}
