package ru.nsu.concerts_mate.users_service.services.cities.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.CitySearchGrpc;
import ru.nsu.concerts_mate.ElasticService;
import ru.nsu.concerts_mate.users_service.model.dto.CityDto;
import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;
import ru.nsu.concerts_mate.users_service.services.cities.*;

import java.util.List;

@Service
public class ElasticSearchService implements CitiesService {
    @GrpcClient("elastic-service")
    private CitySearchGrpc.CitySearchBlockingStub stub;

    private final ModelMapper modelMapper;

    @Autowired
    public ElasticSearchService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CitySearchByNameResult findCity(String cityName) throws CitiesServiceException {
        final ElasticService.CityNameRequest request = ElasticService.CityNameRequest.newBuilder()
                .setName(cityName)
                .build();

        ElasticService.CitySearchNameResponse response;

        try {
            response = stub.searchByName(request);
        } catch (Exception exception) {
            throw new CitiesServiceException(String.format("Request with \"%s\" failed", cityName), exception);
        }

        System.out.println(response);

        return new CitySearchByNameResult(
                mapCode(response.getCode()),
                mapOptions(response.getOptionsList())
        );
    }

    @Override
    public CitySearchByCoordsResult findCity(CoordsDto coords) throws CitiesServiceException {
        final ElasticService.CoordsRequest request = ElasticService.CoordsRequest.newBuilder()
                .setLat(coords.getLat())
                .setLon(coords.getLon())
                .build();

        ElasticService.CitySearchCoordsResponse response;

        try {
            response = stub.searchByCoords(request);
        } catch (Exception exception) {
            throw new CitiesServiceException(String.format("Request with lat=%f, lon=%f failed", coords.getLat(), coords.getLon()), exception);
        }

        return new CitySearchByCoordsResult(
                mapCode(response.getCode()),
                mapOptions(response.getOptionsList())
        );
    }

    private List<CityDto> mapOptions(List<ElasticService.City> options) {
        return options.stream().map(c -> modelMapper.map(c, CityDto.class)).toList();
    }

    private CitySearchByNameCode mapCode(ElasticService.CitySearchNameResponse.ResponseCode code) throws CitiesServiceException {
        switch (code) {
            case SUCCESS_NAME -> {
                return CitySearchByNameCode.SUCCESS;
            }

            case FUZZY_NAME -> {
                return CitySearchByNameCode.FUZZY;
            }

            case EMPTY_NAME -> {
                return CitySearchByNameCode.NOT_FOUND;
            }

            case INTERNAL_ERROR_NAME -> {
                return CitySearchByNameCode.INTERNAL_ERROR;
            }
        }

        throw new CitiesServiceException(String.format("Received unknown name-search code: %s\n", code));
    }

    private CitySearchByCoordsCode mapCode(ElasticService.CitySearchCoordsResponse.ResponseCode code) throws CitiesServiceException {
        switch (code) {
            case SUCCESS_COORDS -> {
                return CitySearchByCoordsCode.SUCCESS;
            }

            case EMPTY_COORDS -> {
                return CitySearchByCoordsCode.NOT_FOUND;
            }

            case INVALID_COORDS -> {
                return CitySearchByCoordsCode.INVALID_COORDS;
            }

            case INTERNAL_ERROR_COORDS -> {
                return CitySearchByCoordsCode.INTERNAL_ERROR;
            }
        }

        throw new CitiesServiceException(String.format("Received unknown coords-search code: %s\n", code));
    }
}
