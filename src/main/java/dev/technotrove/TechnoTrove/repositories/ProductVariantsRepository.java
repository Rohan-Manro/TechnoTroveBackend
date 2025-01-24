package dev.technotrove.TechnoTrove.repositories;

import dev.technotrove.TechnoTrove.entities.ProductVariants;  // Adjust this line to match your package structure
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantsRepository extends JpaRepository<ProductVariants,Long>{

}
