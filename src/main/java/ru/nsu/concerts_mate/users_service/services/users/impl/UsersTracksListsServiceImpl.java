package ru.nsu.concerts_mate.users_service.services.users.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.UserTracksListDto;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTracksListEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTracksListEntity;
import ru.nsu.concerts_mate.users_service.repositories.UsersRepository;
import ru.nsu.concerts_mate.users_service.repositories.UsersTracksListsRepository;
import ru.nsu.concerts_mate.users_service.services.users.UsersTracksListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListNotAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UsersTracksListsServiceImpl implements UsersTracksListsService {
    private final UsersRepository usersRepository;
    private final UsersTracksListsRepository tracksListsRepository;
    private final ModelMapper modelMapper;

    public UsersTracksListsServiceImpl(UsersRepository usersRepository, UsersTracksListsRepository tracksListsRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.tracksListsRepository = tracksListsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserTracksListDto saveUserTracksList(long telegramId, String tracksListUrl) throws UserNotFoundException, TracksListAlreadyAddedException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserTracksListEntity> testUnique = tracksListsRepository.findById(
                new UserTracksListEmbeddedEntity(foundUser.get().getId(), tracksListUrl)
        );
        if (testUnique.isPresent()) {
            throw new TracksListAlreadyAddedException();
        }

        final UserTracksListEntity userTracksList = new UserTracksListEntity(
                foundUser.get().getId(),
                tracksListUrl
        );
        final UserTracksListEntity savingResult = tracksListsRepository.save(userTracksList);
        if (!savingResult.equals(userTracksList)) {
            throw new InternalErrorException();
        }

        return modelMapper.map(savingResult, UserTracksListDto.class);
    }

    @Override
    public UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserTracksListEntity> testUnique = tracksListsRepository.findById(
                new UserTracksListEmbeddedEntity(foundUser.get().getId(), cityName)
        );
        if (testUnique.isEmpty()) {
            throw new TracksListNotAddedException();
        }

        final UserTracksListEntity userTracksList = new UserTracksListEntity(foundUser.get().getId(), cityName);
        tracksListsRepository.delete(userTracksList);

        return modelMapper.map(userTracksList, UserTracksListDto.class);
    }

    @Override
    public List<String> getUserTracksLists(long telegramId) throws UserNotFoundException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var userTracksLists = tracksListsRepository.getUserTracksLists(
                foundUser.get().getId()
        );
        if (userTracksLists.isEmpty()) {
            throw new InternalErrorException();
        }

        return userTracksLists.get();
    }
}
