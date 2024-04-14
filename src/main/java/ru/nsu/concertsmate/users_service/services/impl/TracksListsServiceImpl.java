package ru.nsu.concertsmate.users_service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.users_service.model.dto.UserTracksListDto;
import ru.nsu.concertsmate.users_service.model.entities.UserEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserTracksListEmbeddedEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserTracksListEntity;
import ru.nsu.concertsmate.users_service.repositories.TracksListsRepository;
import ru.nsu.concertsmate.users_service.repositories.UsersRepository;
import ru.nsu.concertsmate.users_service.services.TracksListsService;
import ru.nsu.concertsmate.users_service.services.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.TracksListNotAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TracksListsServiceImpl implements TracksListsService {
    private final TracksListsRepository tracksListsRepository;
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;

    @Autowired
    public TracksListsServiceImpl(TracksListsRepository tracksListsRepository, ModelMapper modelMapper, UsersRepository usersRepository1) {
        this.tracksListsRepository = tracksListsRepository;
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository1;
    }

    @Override
    public UserTracksListDto saveUserTracksList(long telegramId, String tracksListUrl) throws UserNotFoundException, TracksListAlreadyAddedException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<UserTracksListEntity> testUnique = tracksListsRepository.findById(new UserTracksListEmbeddedEntity(user.get().getId(), tracksListUrl));
        if (testUnique.isPresent()) {
            throw new TracksListAlreadyAddedException();
        }
        UserTracksListEntity userTracksList = new UserTracksListEntity(user.get().getId(), tracksListUrl);
        UserTracksListEntity result = tracksListsRepository.save(userTracksList);
        if (!result.equals(userTracksList)) {
            throw new RuntimeException("can't save user tracks-list");
        }

        return modelMapper.map(result, UserTracksListDto.class);
    }

    @Override
    public UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<UserTracksListEntity> testUnique = tracksListsRepository.findById(new UserTracksListEmbeddedEntity(user.get().getId(), cityName));
        if (testUnique.isEmpty()) {
            throw new TracksListNotAddedException();
        }
        UserTracksListEntity userTracksList = new UserTracksListEntity(user.get().getId(), cityName);
        tracksListsRepository.delete(userTracksList);

        return null;
    }

    @Override
    public List<String> getUserTracksLists(long telegramId) throws UserNotFoundException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return tracksListsRepository.getUserTracksLists(user.get().getId()).get();
    }
}
