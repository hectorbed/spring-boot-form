package com.udemy.springboot.form.app.services;

import java.util.List;

import com.udemy.springboot.form.app.models.domain.Role;

public interface RoleService {
	
	public List<Role> list();
	
	public Role getById(Integer id);

}
