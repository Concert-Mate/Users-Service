package ru.nsu.concerts_mate.users_service.services.cities.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesService;

import ru.nsu.concerts_mate.CitySearchGrpc;
import ru.nsu.concerts_mate.ElasticService
;
@Service
public class ElasticSearchService implements CitiesService {
    @GrpcClient("hello")
    private CitySearchGrpc.CitySearchBlockingStub stub;

    @Override
    public void findCity(String cityName) {
        final ElasticService.CityNameRequest request = ElasticService.CityNameRequest.newBuilder()
                .setName(cityName)
                .build();

        final ElasticService.CitySearchNameResponse response = stub.searchByName(request);
        System.out.println(response.getCode());
        for (final var it : response.getOptionsList()) {
            System.out.println(it.getName());
        }
        System.out.println("__________________");
    }

    @Override
    public void findCity(CoordsDto coords) {

    }
}
