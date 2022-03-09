package com.udemy.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udemy.springboot.form.app.services.CountryService;

@Component
public class CountryPropertyEditor extends PropertyEditorSupport{

	@Autowired
	private CountryService service;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			Integer idConverted = Integer.parseInt(id);
			this.setValue(service.getById(idConverted));
		}catch(NumberFormatException e) {
			this.setValue(null);
		}
	}
	
}
