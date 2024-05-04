package ru.nsu.concerts_mate.users_service.services.users.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.ShownConcertDto;
import ru.nsu.concerts_mate.users_service.model.entities.ShownConcertEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.ShownConcertEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;
import ru.nsu.concerts_mate.users_service.repositories.ShownConcertsRepository;
import ru.nsu.concerts_mate.users_service.repositories.UsersRepository;
import ru.nsu.concerts_mate.users_service.services.users.UsersShownConcertsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.ShownConcertAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.ShownConcertNotFoundException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersShownConcertsServiceImpl implements UsersShownConcertsService {
    private final UsersRepository usersRepository;
    private final ShownConcertsRepository shownConcertsRepository;
    private final ModelMapper modelMapper;

    @Override
    public ShownConcertDto saveShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertAlreadyAddedException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<ShownConcertEntity> testUnique = shownConcertsRepository.findById(
                new ShownConcertEmbeddedEntity(foundUser.get().getId(), concertUrl)
        );
        if (testUnique.isPresent()) {
            throw new ShownConcertAlreadyAddedException();
        }

        final ShownConcertEntity shownConcertEntity = new ShownConcertEntity(
                foundUser.get().getId(),
                concertUrl
        );
        final ShownConcertEntity savingResult = shownConcertsRepository.save(shownConcertEntity);
        if (!savingResult.equals(shownConcertEntity)) {
            throw new InternalErrorException();
        }

        return modelMapper.map(savingResult, ShownConcertDto.class);
    }

    @Override
    public ShownConcertDto deleteShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertNotFoundException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<ShownConcertEntity> testUnique = shownConcertsRepository.findById(
                new ShownConcertEmbeddedEntity(foundUser.get().getId(), concertUrl)
        );
        if (testUnique.isEmpty()) {
            throw new ShownConcertNotFoundException();
        }

        final ShownConcertEntity userShownConcert = new ShownConcertEntity(foundUser.get().getId(), concertUrl);
        shownConcertsRepository.delete(userShownConcert);

        return modelMapper.map(userShownConcert, ShownConcertDto.class);
    }

    @Override
    public List<String> getShownConcerts(long telegramId) throws UserNotFoundException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var userShownConcerts = shownConcertsRepository.getUserShownConcerts(
                foundUser.get().getId()
        );
        if (userShownConcerts.isEmpty()) {
            throw new InternalErrorException();
        }

        return userShownConcerts.get();
    }

    @Override
    public boolean hasShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<ShownConcertEntity> testUnique = shownConcertsRepository.findById(
                new ShownConcertEmbeddedEntity(foundUser.get().getId(), concertUrl)
        );
        return testUnique.isPresent();
    }
}
