package rw.ac.rca.termOneExam.domain;

import javax.persistence.*;

@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private double weather;
	
	@Transient
	private double fahrenheit;

	public City() {
		super();
	}
	
	public City(String name, double weather) {
		super();
		this.name = name;
		this.weather = weather;
	}

	public City(long id, String name, double weather) {
		this.id = id;
		this.name = name;
		this.weather = weather;
	}

	public City(long id, String name, double weather, double fahrenheit) {
		super();
		this.id = id;
		this.name = name;
		this.weather = weather;
		this.fahrenheit = fahrenheit;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeather() {
		return weather;
	}

	public void setWeather(double weather) {
		this.weather = weather;
	}

	public double getFahrenheit() {
		return fahrenheit;
	}

	public void setFahrenheit(double fahrenheit) {
		this.fahrenheit = fahrenheit;
	}
	
}
