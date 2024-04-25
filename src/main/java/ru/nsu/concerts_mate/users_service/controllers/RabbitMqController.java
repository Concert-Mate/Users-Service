package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.rabbitmq.RabbitMqApi;
import ru.nsu.concerts_mate.users_service.model.dto.ArtistDto;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.PriceDto;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;
import ru.nsu.concerts_mate.users_service.services.broker.BrokerEvent;
import ru.nsu.concerts_mate.users_service.services.broker.BrokerService;
import ru.nsu.concerts_mate.users_service.services.users.UsersService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RabbitMqController implements RabbitMqApi {
    private final BrokerService rabbitMqService;
    private final UsersService usersService;

    @Autowired
    public RabbitMqController(BrokerService rabbitMqService, UsersService usersService) {
        this.rabbitMqService = rabbitMqService;
        this.usersService = usersService;
    }

    @Override
    public String emit() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        final List<UserDto> users = usersService.findAllUsers();
        final List<ConcertDto> concerts = new ArrayList<>();

        try {
            concerts.add(new ConcertDto(
                    "Дора",
                    "https://afisha.yandex.ru/novosibirsk/concert/dora-2024-05-03",
                    "Новосибирск",
                    "Подземка",
                    "Красный просп., 161",
                    dateFormat.parse("2024-05-03 20:00:00+07:00"),
                    "https://maps.yandex.ru/?z=16&ll=82.911402,55.061446&pt=82.911402,55.061446",
                    List.of("https://avatars.mds.yandex.net/get-afishanew/31447/9bd67f14763a75218410d90c07a10708/orig"),
                    new PriceDto(1500, "RUB"),
                    List.of(new ArtistDto("Дора", 6826935))
            ));
            concerts.add(new ConcertDto(
                    "Б.А.У.",
                    "https://afisha.yandex.ru/cheboksary/concert/b-a-u-tour",
                    "Чебоксары",
                    "SK Bar",
                    "ул. Карла Маркса, 47",
                    dateFormat.parse("2024-04-16 19:00:00+03:00"),
                    "https://maps.yandex.ru/?z=16&ll=47.247889,56.134405&pt=47.247889,56.134405",
                    List.of("https://avatars.mds.yandex.net/get-afishanew/28638/b8b59918da3749ff123d7530fc1d6850/orig"),
                    new PriceDto(1500, "RUB"),
                    List.of(new ArtistDto("Б.А.У.", 3680757))
            ));
        } catch (Exception ignored) {}

        try {
            for (final var user : users) {
                rabbitMqService.sendEvent(new BrokerEvent(user, concerts));
            }
            return "Success";
        } catch (Exception exception) {
            return "Failure: " + exception.getMessage();
        }
    }
}
