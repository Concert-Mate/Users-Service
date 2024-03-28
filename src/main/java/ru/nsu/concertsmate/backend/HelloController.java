package ru.nsu.concertsmate.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.nsu.concertsmate.backend.services.CityService;

@RestController
public class HelloController {

    private CityService cityService;

    @Autowired
    public HelloController(CityService cityService){
        this.cityService = cityService;
    }

    @GetMapping(value = "/search/{city}")
    public String hello(@Size(max = 128) @NotBlank(message = "city must be not blank") @PathVariable("city") String city) {
        cityService.getCityByName(city);
        return cityService.getCityByName(city).toString();
    }
}
