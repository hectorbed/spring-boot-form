package com.udemy.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udemy.springboot.form.app.models.domain.Country;

@Service
public class CountryServiceImpl implements CountryService {

	private List<Country> list;
	
	public CountryServiceImpl() {
		this.list = Arrays.asList(new Country(1, "CO", "Colombia"),
				new Country(2, "MX", "Mexico"),
				new Country(3, "EU", "EEUU"), 
				new Country(4, "BR", "Brasil"));
	}

	@Override
	public List<Country> list() {
		return this.list;
	}

	@Override
	public Country getById(Integer id) {
		for(Country c : this.list) {
			if(c.getId() == id) {
				return c;
			}
		}
		return null;
	}

}
