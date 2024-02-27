package com.rt.pot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	// public boolean existByCategoryName(String categoryName);

	public Category findByCategoryName(String categoryName);

}
