package com.facedynamics.notifications.events;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.PostDisliked;
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
public class PostDislikedEventListener {

    private final EmailService emailService;
    /**
     * Represents the map of postIds, which were disliked and corresponding events
     * were triggered. Number of notifications in list equals to number of likes.
     */
    private static final Map<Long, List<PostDislikeEvent>> listMap = new ConcurrentHashMap<>();
    private static Long currentTime = System.currentTimeMillis();

    @Value("${source.mail.islike.delaymillis}")
    private long delay;

    @EventListener(PostDislikeEvent.class)
    public void onPostDislikedEvent(PostDislikeEvent event){
        if (event != null) {
            NotificationDto dto = event.getReceivedDTO();
            if (dto.content() instanceof PostDisliked postDisliked) {
                Long postId = postDisliked.getPostId();
                if (listMap.containsKey(postId)) {
                    List<PostDislikeEvent> listInside = listMap.get(postId);
                    listInside.add(event);
                    listMap.put(postId, listInside);
                } else {
                    List<PostDislikeEvent> list = new ArrayList<>();
                    list.add(event);
                    listMap.put(postId, list);
                }
            }
        }

        if (currentTime + delay < System.currentTimeMillis()) {
            if (!listMap.isEmpty()){
                for (Map.Entry<Long, List<PostDislikeEvent>> entry: listMap.entrySet()){
                    List<PostDislikeEvent> events = entry.getValue();
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
     * Invokes onPostDislikedEvent() method
     * when cached map is not Empty but email notifications
     * must be delivered.
     */
    @Scheduled(fixedDelay = 60000)
    public void fixedDelaySch() {
        onPostDislikedEvent(null);
    }
}
