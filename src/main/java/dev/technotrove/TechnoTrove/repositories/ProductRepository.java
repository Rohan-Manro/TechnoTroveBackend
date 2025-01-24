package dev.technotrove.TechnoTrove.repositories;

import dev.technotrove.TechnoTrove.entities.Product;  // Adjust this line to match your package structure
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
