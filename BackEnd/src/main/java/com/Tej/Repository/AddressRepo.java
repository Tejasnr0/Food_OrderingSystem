package com.Tej.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.Address;

public interface AddressRepo extends JpaRepository<Address,Long>{
	
}
