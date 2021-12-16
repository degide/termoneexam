package rw.ac.rca.termOneExam.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.service.CityService;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private CityService cityServiceMock;

    @Test
    public void getAll_success() throws Exception {
        List<City> cities = Arrays.asList(
                new City(101,"Kigali",24),
                new City(102,"Musanze",18),
                new City(103,"Rubavu",20)
        );
        when(cityServiceMock.getAll()).thenReturn(cities);
        ResponseEntity<?> response = testRestTemplate.getForEntity("/api/cities/all", List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, ((List) Objects.requireNonNull(response.getBody())).size());
    }

    @Test
    public void add_success(){
        when(cityServiceMock.existsByName(any(String.class))).thenReturn(false);
        when(cityServiceMock.save(any(CreateCityDTO.class))).thenReturn(new City(101,"Kigali",24));
        ResponseEntity<City> response = testRestTemplate.postForEntity("/api/cities/add", new CreateCityDTO(
            "Kigali", 24
        ), City.class);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(101, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void add_exists(){
        when(cityServiceMock.existsByName("Kigali")).thenReturn(true);
        ResponseEntity<APICustomResponse> response = testRestTemplate.postForEntity("/api/cities/add", new CreateCityDTO(
                "Kigali", 24
        ), APICustomResponse.class);
        assertFalse(Objects.requireNonNull(response.getBody()).isStatus());
        assertEquals("City name Kigali is registered already", response.getBody().getMessage());
    }

    @Test
    public void getById_success(){
        when(cityServiceMock.getById(any(long.class))).thenReturn(Optional.of(new City(101,"Kigali",24)));
        ResponseEntity<City> response = testRestTemplate.getForEntity("/api/cities/id/101", City.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(101, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void getById_notFound(){
        when(cityServiceMock.getById(106L)).thenReturn(Optional.empty());
        ResponseEntity<APICustomResponse> response = testRestTemplate.getForEntity("/api/cities/id/106", APICustomResponse.class);
        assertEquals(404, response.getStatusCodeValue());
        assertFalse(Objects.requireNonNull(response.getBody()).isStatus());
        assertEquals("City not found with id 106", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
