package com.rt.pot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.pot.model.Admin;
import com.rt.pot.model.Category;
import com.rt.pot.model.Product;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProductsRepo extends JpaRepository<Product, Integer> {
	// public boolean existByProductName(String productName);
	public Product findByProductName(String productname);

	@Query(value = "SELECT * FROM product_details WHERE admin_id=:adminId", nativeQuery = true)
	public List<Product> findByAdminId(@Param("adminId") Integer adminId);

	Product findByProductNameAndBrandNameAndDescriptionAndDiscountPercentageAndProductPriceAndImageUrlAndAdminData(
			String productName, String brandName, String description, double discountPercentage, double productPrice,
			String imageUrl, Admin adminData);

	@Query("SELECT p FROM Product p " + "LEFT JOIN p.category c "
			+ "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(p.brandName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	public List<Product> findByKeyword(@Param("keyword") String keyword);

	@Query("SELECT p FROM Product p  LEFT JOIN p.category c WHERE  c.categoryName=:categoryName")
	public List<Product> findByCategoryName(@Param("categoryName") String categoryName);

	public List<Product> findAllByOrderByProductPriceAsc();

	public List<Product> findAllByOrderByProductPriceDesc();

	@Modifying
	@Query(value = "UPDATE product_details SET product_qty = :newQty WHERE product_id = :productId", nativeQuery = true)
	public int updateProductQty(@Param("newQty") Integer qty, @Param("productId") Integer productId);

	public List<Product> findByCategory(Category category);

	@Query(value = "SELECT brand_name FROM product_details WHERE category_id=?1", nativeQuery = true)

	public List<String> findBrandAccordingToCategoryId(Integer categoryId);

	@Query(value = "SELECT * FROM product_details WHERE UPPER(brand_Name)=?1 and category_id=?2", nativeQuery = true)
	public List<Product> findProductByBrandInSpecificCategory(String brandName, Integer categoryId);

	@Modifying
	@Query(value = "UPDATE product_details SET product_qty = :newQty WHERE product_id = :productId AND admin_id = :sellerId", nativeQuery = true)
	public Integer refillProduct(@Param("sellerId") Integer sellerId, @Param("newQty") Integer newProductQty,
			@Param("productId") Integer productId);
}
