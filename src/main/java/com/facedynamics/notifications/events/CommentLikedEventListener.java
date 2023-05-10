package com.facedynamics.notifications.events;

import com.facedynamics.notifications.dto.CommentLiked;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class CommentLikedEventListener {

    private final EmailService emailService;
    /**
     * Represents the map of commentIds, which were liked and corresponding events
     * were triggered. Number of notifications in list equals to number of likes.
     */
    private static final Map<Long, List<CommentLikeEvent>> listMap = new ConcurrentHashMap<>();
    private static Long currentTime = System.currentTimeMillis();

    @Value("${source.mail.islike.delaymillis}")
    private long delay;

    @EventListener(CommentLikeEvent.class)
    public void onCommentLikedEvent(CommentLikeEvent event){
        if (event != null) {
            NotificationDto dto = event.getReceivedDTO();
            if (dto.content() instanceof CommentLiked commentLiked) {
                Long commentId = commentLiked.getCommentId();
                if (listMap.containsKey(commentId)) {
                    List<CommentLikeEvent> listInside = listMap.get(commentId);
                    listInside.add(event);
                    listMap.put(commentId, listInside);
                } else {
                    List<CommentLikeEvent> list = new ArrayList<>();
                    list.add(event);
                    listMap.put(commentId, list);
                }
            }
        }

        if (currentTime + delay < System.currentTimeMillis()) {
            if (!listMap.isEmpty()){
                for (Map.Entry<Long, List<CommentLikeEvent>> entry: listMap.entrySet()){
                    List<CommentLikeEvent> events = entry.getValue();
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
     * Invokes onCommentLikedEvent() method
     * when cached map is not Empty but email notification
     * must be delivered.
     */
    @Scheduled(fixedDelay = 60000)
    public void fixedDelaySch() {
        onCommentLikedEvent(null);
    }
}
