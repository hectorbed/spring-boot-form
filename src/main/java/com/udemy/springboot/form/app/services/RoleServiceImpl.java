package com.udemy.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udemy.springboot.form.app.models.domain.Role;

@Service
public class RoleServiceImpl implements RoleService {

	private List<Role> rolesList;
	
	public RoleServiceImpl() {
		this.rolesList = Arrays.asList(new Role(1, "ROLE_ADMIN"),
				new Role(2, "ROLE_USER"),
				new Role(3, "ROLE_MODERATOR"));
	}

	@Override
	public List<Role> list() {
		return this.rolesList;
	}

	@Override
	public Role getById(Integer id) {
		for(Role rol: this.rolesList) {
			if(id == rol.getId()) {
				return rol;
			}
		}
		return null;
	}

}
