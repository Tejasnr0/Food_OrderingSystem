 package com.Tej.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.User;

public interface Userrepo extends JpaRepository<User, Long> {
	
	public User findByEmail(String mail);

	public User findByfullName(String fullName);
	
	

}
