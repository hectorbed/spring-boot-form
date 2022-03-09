package com.udemy.springboot.form.app.services;

import java.util.List;

import com.udemy.springboot.form.app.models.domain.Country;

public interface CountryService {
	
	public List<Country> list();
	public Country getById(Integer id);
	

}
