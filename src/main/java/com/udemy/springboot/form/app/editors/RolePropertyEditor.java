package com.udemy.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udemy.springboot.form.app.services.RoleService;

@Component
public class RolePropertyEditor extends PropertyEditorSupport {

	@Autowired
	private RoleService service;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			this.setValue(service.getById(Integer.parseInt(id)));
		}catch(NumberFormatException ex) {
			this.setValue(null);
		}

	}

	
}
