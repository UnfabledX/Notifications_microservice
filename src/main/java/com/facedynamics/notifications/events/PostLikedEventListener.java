package com.facedynamics.notifications.events;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostLiked;
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
public class PostLikedEventListener {

    private final EmailService emailService;
    /**
     * Represents the map of postIds, which were liked and corresponding events
     * were triggered. Number of notifications in list equals to number of likes.
     */
    private static final Map<Long, List<PostLikeEvent>> listMap = new ConcurrentHashMap<>();
    private static Long currentTime = System.currentTimeMillis();

    @Value("${source.mail.islike.delaymillis}")
    private long delay;

    @EventListener(PostLikeEvent.class)
    public void onPostLikedEvent(PostLikeEvent event){
        if (event != null) {
            NotificationDto dto = event.getReceivedDTO();
            NotificationContent<PostLiked> content = dto.content();
            PostLiked postLiked = content.getChild();
            Long postId = postLiked.getPostId();
            if (listMap.containsKey(postId)) {
                List<PostLikeEvent> listInside = listMap.get(postId);
                listInside.add(event);
                listMap.put(postId, listInside);
            } else {
                List<PostLikeEvent> list = new ArrayList<>();
                list.add(event);
                listMap.put(postId, list);
            }
        }

        if (currentTime + delay < System.currentTimeMillis()) {
            if (!listMap.isEmpty()){
                for (Map.Entry<Long, List<PostLikeEvent>> entry: listMap.entrySet()){
                    List<PostLikeEvent> events = entry.getValue();
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
     * Invokes onPostLikedEvent() method
     * when cached map is not Empty but email notification
     * must be delivered.
     */
    @Scheduled(fixedDelay = 60000)
    public void fixedDelaySch() {
        onPostLikedEvent(null);
    }
}
