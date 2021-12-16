package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository iCityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getAll_success(){
        when(iCityRepository.findAll()).thenReturn(Arrays.asList(
                new City(101,"Kigali",24),
                new City(102,"Musanze",18),
                new City(103,"Rubavu",20)
        ));
        List<City> cities = cityService.getAll();
        assertEquals(3, cities.size());
        assertEquals(75.2, cities.get(0).getFahrenheit(), 1e-15);
        assertEquals("Musanze", cities.get(1).getName());
    }

    @Test
    public void getById_success(){
        when(iCityRepository.findById(101L)).thenReturn(Optional.of(new City(101, "Kigali", 24)));
        Optional<City> city = cityService.getById(101);
        assertTrue(city.isPresent());
        assertEquals(101, city.get().getId());
        assertEquals(75.2, city.get().getFahrenheit(), 1e-15);
    }

    @Test
    public void getById_notFound(){
        when(iCityRepository.findById(109L)).thenReturn(Optional.empty());
        Optional<City> city = cityService.getById(109);
        assertFalse(city.isPresent());
    }

    @Test
    public void save_success(){
        when(iCityRepository.save(any(City.class))).thenReturn(new City(101,"Kigali",24));
        City savedCity = cityService.save(new CreateCityDTO("Kigali", 24));
        assertEquals(101, savedCity.getId());
        assertEquals(75.2, savedCity.getFahrenheit(), 1e-15);
    }

    @Test
    public void existsByName_success(){
        when(iCityRepository.existsByName("Kigali")).thenReturn(true);
        assertTrue(cityService.existsByName("Kigali"));
    }

    @Test
    public void existsByName_notFound(){
        when(iCityRepository.existsByName("Bujumbura")).thenReturn(false);
        assertFalse(cityService.existsByName("Bujumbura"));
    }
}
