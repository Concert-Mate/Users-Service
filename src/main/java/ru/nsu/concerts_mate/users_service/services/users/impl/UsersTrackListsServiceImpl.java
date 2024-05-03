package ru.nsu.concerts_mate.users_service.services.users.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.UserTrackListDto;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTrackListEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTrackListEntity;
import ru.nsu.concerts_mate.users_service.repositories.UsersRepository;
import ru.nsu.concerts_mate.users_service.repositories.UsersTrackListsRepository;
import ru.nsu.concerts_mate.users_service.services.users.UsersTrackListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListNotAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UsersTrackListsServiceImpl implements UsersTrackListsService {
    private final UsersRepository usersRepository;
    private final UsersTrackListsRepository tracksListsRepository;
    private final ModelMapper modelMapper;

    public UsersTrackListsServiceImpl(UsersRepository usersRepository, UsersTrackListsRepository tracksListsRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.tracksListsRepository = tracksListsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserTrackListDto saveUserTrackList(long telegramId, String tracksListUrl) throws UserNotFoundException, TracksListAlreadyAddedException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserTrackListEntity> testUnique = tracksListsRepository.findById(
                new UserTrackListEmbeddedEntity(foundUser.get().getId(), tracksListUrl)
        );
        if (testUnique.isPresent()) {
            throw new TracksListAlreadyAddedException();
        }

        final UserTrackListEntity userTracksList = new UserTrackListEntity(
                foundUser.get().getId(),
                tracksListUrl
        );
        final UserTrackListEntity savingResult = tracksListsRepository.save(userTracksList);
        if (!savingResult.equals(userTracksList)) {
            throw new InternalErrorException();
        }

        return modelMapper.map(savingResult, UserTrackListDto.class);
    }

    @Override
    public UserTrackListDto deleteUserTrackList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserTrackListEntity> testUnique = tracksListsRepository.findById(
                new UserTrackListEmbeddedEntity(foundUser.get().getId(), cityName)
        );
        if (testUnique.isEmpty()) {
            throw new TracksListNotAddedException();
        }

        final UserTrackListEntity userTracksList = new UserTrackListEntity(foundUser.get().getId(), cityName);
        tracksListsRepository.delete(userTracksList);

        return modelMapper.map(userTracksList, UserTrackListDto.class);
    }

    @Override
    public List<String> getUserTrackLists(long telegramId) throws UserNotFoundException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var userTracksLists = tracksListsRepository.getUserTrackLists(
                foundUser.get().getId()
        );
        if (userTracksLists.isEmpty()) {
            throw new InternalErrorException();
        }

        return userTracksLists.get();
    }
}
