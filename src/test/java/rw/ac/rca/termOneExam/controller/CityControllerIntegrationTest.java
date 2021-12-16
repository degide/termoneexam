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

    @Test
    public void getAll_success() throws Exception {
        ResponseEntity<City[]> response = testRestTemplate.getForEntity("/api/cities/all", City[].class);
        List<City> cities = Arrays.asList(Objects.requireNonNull(response.getBody()));
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(4, cities.size());
    }

    @Test
    public void add_success(){
        ResponseEntity<City> response = testRestTemplate.postForEntity("/api/cities/add", new CreateCityDTO(
                "Gisenyi", 21
        ), City.class);
        System.out.println(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Gisenyi", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    public void add_exists(){
        ResponseEntity<APICustomResponse> response = testRestTemplate.postForEntity("/api/cities/add", new CreateCityDTO(
                "Kigali", 24
        ), APICustomResponse.class);
        assertFalse(Objects.requireNonNull(response.getBody()).isStatus());
        assertEquals("City name Kigali is registered already", response.getBody().getMessage());
    }

    @Test
    public void getById_success(){
        ResponseEntity<City> response = testRestTemplate.getForEntity("/api/cities/id/101", City.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(101, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void getById_notFound(){
        ResponseEntity<APICustomResponse> response = testRestTemplate.getForEntity("/api/cities/id/109", APICustomResponse.class);
        assertEquals(404, response.getStatusCodeValue());
        assertFalse(Objects.requireNonNull(response.getBody()).isStatus());
        assertEquals("City not found with id 109", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
