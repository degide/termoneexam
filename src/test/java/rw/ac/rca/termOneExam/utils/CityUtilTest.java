package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityUtilTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Mock
    private ICityRepository iCityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void noCityMoreThan40DegreeCelsius_test(){
        ResponseEntity<City[]> response = testRestTemplate.getForEntity("/api/cities/all", City[].class);
        List<City> cities = Arrays.asList(Objects.requireNonNull(response.getBody()));
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(cities.stream().allMatch(city -> city.getWeather()<=40));
    }

    @Test
    public void noCityLessThan10DegreeCelsius_test(){
        ResponseEntity<City[]> response = testRestTemplate.getForEntity("/api/cities/all", City[].class);
        List<City> cities = Arrays.asList(Objects.requireNonNull(response.getBody()));
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(cities.stream().allMatch(city -> city.getWeather()>=10));
    }

    @Test
    public void citiesContainsKigaliAndMusanze_test(){
        ResponseEntity<City[]> response = testRestTemplate.getForEntity("/api/cities/all", City[].class);
        List<City> cities = Arrays.asList(Objects.requireNonNull(response.getBody()));
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(cities.stream().anyMatch(city -> city.getName().equalsIgnoreCase("Kigali")) &&
                        cities.stream().anyMatch(city -> city.getName().equalsIgnoreCase("Musanze"))
        );
    }

    @Test
    public void testMock(){
        when(iCityRepository.findAll()).thenReturn(Arrays.asList(
                new City(101,"Kigali",24),
                new City(102,"Musanze",18),
                new City(103,"Rubavu",20)
        ));
        List<City> cities = cityService.getAll();
        assertEquals(3, cities.size());
        assertEquals(101, cities.get(0).getId());
    }
}
