package ru.nsu.concerts_mate.users_service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.nsu.concerts_mate.users_service.model.dto.ArtistDto;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;
import ru.nsu.concerts_mate.users_service.services.broker.BrokerEvent;
import ru.nsu.concerts_mate.users_service.services.broker.BrokerException;
import ru.nsu.concerts_mate.users_service.services.broker.BrokerService;
import ru.nsu.concerts_mate.users_service.services.music.MusicService;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concerts_mate.users_service.services.users.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.users.UsersService;
import ru.nsu.concerts_mate.users_service.services.users.UsersShownConcertsService;
import ru.nsu.concerts_mate.users_service.services.users.UsersTrackListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class ConcertsScheduler {
    private final UsersService usersService;
    private final UsersCitiesService usersCitiesService;
    private final UsersTrackListsService usersTrackListsService;
    private final MusicService musicService;
    private final UsersShownConcertsService shownConcertsService;
    private final BrokerService brokerService;

    @Autowired
    public ConcertsScheduler(UsersService usersService, UsersCitiesService usersCitiesService, UsersTrackListsService usersTrackListsService, MusicService musicService, UsersShownConcertsService shownConcertsService, BrokerService service) {
        this.usersService = usersService;
        this.usersCitiesService = usersCitiesService;
        this.usersTrackListsService = usersTrackListsService;
        this.musicService = musicService;
        this.shownConcertsService = shownConcertsService;
        this.brokerService = service;
    }


    private void fillArtistsForUsers(List<String> playLists, Map<Integer, List<UserDto>> artistsForUsers, UserDto user){
        for (String playList: playLists){
            try {
                List<ArtistDto> artists = musicService.getPlayListData(playList).getArtists();
                for (ArtistDto artist: artists){
                    var mapItem = artistsForUsers.computeIfAbsent(artist.getYandexMusicId(), k -> new ArrayList<>());
                    mapItem.add(user);
                }
            } catch (InternalErrorException ignored) {
                //TODO logging
            } catch (MusicServiceException e) {
                deleteUserPlayListNoExcept(user.getTelegramId(), playList);
            }
        }
    }


    private void deleteUserPlayListNoExcept(long telegramId, String playListUrl){
        try{
            usersTrackListsService.deleteUserTrackList(telegramId, playListUrl);
        } catch (Exception ignored) {
            //TODO logging
        }
    }


    private void sendConcertToUser(List<ConcertDto> concerts, UserDto user){
        try {
            brokerService.sendEvent(new BrokerEvent(user, concerts));
        } catch (BrokerException ignored) {
            //TODO logging
        }
        for (ConcertDto concert: concerts){
            try {
                shownConcertsService.saveShownConcert(user.getTelegramId(), concert.getAfishaUrl());
            } catch (Exception ignored){
                //TODO logging
            }
        }
    }


    private boolean isConcertSent(ConcertDto concert, UserDto user){
        try {
            return shownConcertsService.hasShownConcert(user.getTelegramId(), concert.getAfishaUrl());
        } catch (Exception e) {
            //TODO logging
            return false;
        }
    }


    @Scheduled(fixedRateString = "${spring.scheduler.fixed_rate}") // 30 min
    public void updateConcerts(){
        List<UserDto> users = usersService.findAllUsers();
        Map<Integer, List<UserDto>> artistsForUsers = new HashMap<>();
        Map<Long, List<String>> citiesForUsers = new HashMap<>();
        for (UserDto user: users){
            try {
                List<String> userCities =  usersCitiesService.getUserCities(user.getTelegramId());
                citiesForUsers.put(user.getTelegramId(), userCities);
            } catch (Exception ignored) {
                //TODO logging
                continue;
            }
            try {
                List<String> playLists = usersTrackListsService.getUserTrackLists(user.getTelegramId());
                fillArtistsForUsers(playLists, artistsForUsers, user);
            } catch (Exception ignored) {
                //TODO logging
            }
        }
        Map<UserDto, List<ConcertDto>> concertsForUsers = new HashMap<>();
        for (Map.Entry<Integer, List<UserDto>> entry: artistsForUsers.entrySet()){
            try {
                List<ConcertDto> concerts = musicService.getConcertsByArtistId(entry.getKey());
                for (ConcertDto concert: concerts){
                    for (UserDto user: entry.getValue()){
                        List<String> userCities = citiesForUsers.get(user.getTelegramId());
                        if (userCities.contains(concert.getCity()) && !isConcertSent(concert, user)){
                            var mapItem = concertsForUsers.computeIfAbsent(user, k -> new ArrayList<>());
                            mapItem.add(concert);
                        }
                    }
                }
            } catch (Exception ignored) {
                //TODO logging
            }
        }
        for (Map.Entry<UserDto, List<ConcertDto>> entry: concertsForUsers.entrySet()){
            sendConcertToUser(entry.getValue(), entry.getKey());
        }
        System.out.println("scheduling is finished");
    }
}
