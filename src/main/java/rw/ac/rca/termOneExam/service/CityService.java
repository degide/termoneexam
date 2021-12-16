package rw.ac.rca.termOneExam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@Service
public class CityService {

	@Autowired
	private ICityRepository cityRepository;
	
	public Optional<City> getById(long id) {
		Optional<City> optionalCity = cityRepository.findById(id);
		if(optionalCity.isPresent()){
			City city = new City(
					optionalCity.get().getId(),
					optionalCity.get().getName(),
					optionalCity.get().getWeather()
			);
			city.setFahrenheit((city.getWeather() * 9/5) + 32);
			return Optional.of(city);
		}
		return optionalCity;
	}

	public List<City> getAll() {
		List<City> cities = new ArrayList<>();
		cityRepository.findAll().forEach(city -> {
			city.setFahrenheit((city.getWeather() * 9/5) + 32);
			cities.add(city);
		});
		return cities;
	}

	public boolean existsByName(String name) {
		
		return cityRepository.existsByName(name);
	}

	public City save(CreateCityDTO dto) {
		City newCity =  new City(dto.getName(), dto.getWeather());
		City savedCity = cityRepository.save(newCity);
		savedCity.setFahrenheit((savedCity.getWeather() * 9/5) + 32);
		return savedCity;
	}
	

}
