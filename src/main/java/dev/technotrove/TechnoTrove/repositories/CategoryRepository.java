package dev.technotrove.TechnoTrove.repositories;

import dev.technotrove.TechnoTrove.entities.Category;  // Adjust this line to match your package structure
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long>{

}
