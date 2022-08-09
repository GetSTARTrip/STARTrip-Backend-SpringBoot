package com.startrip.api.service;

import com.startrip.core.dto.favoriteEvent.RequestFavoriteEventDto;
import com.startrip.core.dto.favoriteEvent.ResponseFavoriteEventDto;
import com.startrip.core.dto.favoriteEvent.UpdateFavoriteEventDto;
import com.startrip.core.entity.event.Event;
import com.startrip.core.entity.event.EventRepository;
import com.startrip.core.entity.favorite_event.FavoriteEvent;
import com.startrip.core.entity.favorite_event.FavoriteEventRepository;
import com.startrip.core.entity.user.User;
import com.startrip.core.entity.user.UserRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FavoriteEventService {

    private static UserRepository userRepository;
    private static EventRepository eventRepository;
    private static FavoriteEventRepository favoriteEventRepository;

    private static final Long DELETE_SEC = 1800L; // Timelimit util deletion : 30m
    private static final List<UUID> deleteFEvents = new LinkedList<>();

    @Autowired
    public FavoriteEventService(FavoriteEventRepository favoriteEventRepository,
                                EventRepository eventRepository,
                                UserRepository userRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.favoriteEventRepository = favoriteEventRepository;
    }

    public void createFavoriteEvent (Long userId, RequestFavoriteEventDto dto){
       User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                   throw new IllegalStateException("존재하지 않는 유저입니다");
                });
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 이벤트입니다");
                });

        FavoriteEvent favoriteEvent;
        Optional<FavoriteEvent> FEventFindByEvent = Optional.ofNullable(favoriteEventRepository.findByEventIdAndUserId(event, user));
        if(FEventFindByEvent.isPresent()){
            favoriteEvent = FEventFindByEvent.get();
            favoriteEvent.onValid();
        }else{
            favoriteEvent = FavoriteEvent.of(user, event);
            favoriteEventRepository.save(favoriteEvent);
        }

    }

    public List<ResponseFavoriteEventDto> getFavoriteEvent (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 유저입니다"); });

        Optional<List<FavoriteEvent>> favoriteEvents = Optional.ofNullable(favoriteEventRepository.findAllByUserId(user));
        if(favoriteEvents.isEmpty()){
            throw new RuntimeException("해당 user에 존재하는 좋아요이벤트 정보가 없습니다");
        }

        List<ResponseFavoriteEventDto> responseFavoriteEventDtos = new ArrayList<>();
        for( FavoriteEvent favoriteEvent : favoriteEvents.get() ){
            responseFavoriteEventDtos.add(ResponseFavoriteEventDto.of(favoriteEvent));
        }
        return responseFavoriteEventDtos;
    }

    @Transactional
    public void updateFavoriteEvent (UUID favoriteeventId, UpdateFavoriteEventDto dto){
        FavoriteEvent favoriteEvent = favoriteEventRepository.findById(favoriteeventId)
                .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));
        favoriteEvent.updateIsExcuted(dto);
        favoriteEventRepository.save(favoriteEvent);
    }

    @Transactional
    public void deleteFavoriteEvent (UUID favoriteeventId){
        FavoriteEvent deleteFEvent = favoriteEventRepository.findById(favoriteeventId)
                .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));
        deleteFEvent.offValid();
        favoriteEventRepository.save(deleteFEvent);

        deleteFEvents.add(deleteFEvent.getFavoriteEventId());
    }

    @Scheduled(fixedRate = 1000*600)  // It runs every hour
    @Transactional
    public void deleteFavoriteEventJob(){
        Iterator<UUID> iter;
        for (iter = deleteFEvents.iterator(); iter.hasNext();) {
                UUID deletedItemId = iter.next();
                FavoriteEvent currentItem = favoriteEventRepository.findById(deletedItemId)
                        .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));
                if (currentItem.getIsValid().equals(true)) iter.remove();
                else {
                    deleteTimeoverFavoriteEvent(currentItem);
                    Optional<FavoriteEvent> confirmItem = favoriteEventRepository.findById(deletedItemId);
                    if(confirmItem.isEmpty()) iter.remove();
                }
            }
    }

    @Transactional
    public void deleteTimeoverFavoriteEvent (FavoriteEvent deleteFEvent) {
        LocalDateTime currentTime = LocalDateTime.now();
        Long diffSeconds = ChronoUnit.SECONDS.between(deleteFEvent.getUpdatedDate(), currentTime);
        if (diffSeconds > DELETE_SEC) {
            favoriteEventRepository.deleteById(deleteFEvent.getFavoriteEventId());
        }
    }
}
