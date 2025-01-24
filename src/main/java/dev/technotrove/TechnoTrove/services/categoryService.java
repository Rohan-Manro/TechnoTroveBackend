package dev.technotrove.TechnoTrove.services;

import java.util.List;
import java.util.stream.Collectors;

import dev.technotrove.TechnoTrove.entities.*;
import dev.technotrove.TechnoTrove.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class categoryService {

    @Autowired
    private ProductVariantsRepository productVariantsRepository;   

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public List<ProductVariants> get_all_prods(){
        return productVariantsRepository.findAll();
    }

    @Cacheable(value = "categoryProds", key = "#category")
    public List<ProductVariants> get_cat_prods(String category) {
        List<ProductVariants> allVariants = productVariantsRepository.findAll();

        return allVariants.stream()
                .filter(variant -> {
                    Product product = variant.getProduct_id();
                    if (product != null && product.getCategory_id() != null) {
                        String categoryName = product.getCategory_id().getName();
                        return categoryName != null && categoryName.equalsIgnoreCase(category);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<ProductVariants> get_favs(){
        List<ProductVariants> allVariants = productVariantsRepository.findAll();

        return allVariants.stream()
                .filter(ProductVariants::isFavorite) // Check if 'favorite' is true
                .collect(Collectors.toList()); 
    }

    @CacheEvict(key="#id",value = "favorites", allEntries = true)
    public boolean toggle_fav(Long id) {
        ProductVariants productVariant = productVariantsRepository.findById(id).orElse(null);
    
        if (productVariant == null) {
            throw new RuntimeException("ProductVariant with ID " + id + " not found");
        }
        boolean updatedFavoriteStatus = !productVariant.isFavorite(); // Flip the value
        productVariant.setFavorite(updatedFavoriteStatus);
        productVariantsRepository.save(productVariant);
        return updatedFavoriteStatus;
    }

    public List<ProductVariants> get_cart(){
        List<ProductVariants> allVariants = productVariantsRepository.findAll();

        return allVariants.stream()
                .filter(ProductVariants::isInCart) // Check if 'favorite' is true
                .collect(Collectors.toList()); 
    }   

    @SuppressWarnings("deprecation")
    @CacheEvict(key="#id",value = "add_cart", allEntries = true)
    public boolean add_cart(Long id) {
        ProductVariants productVariant = productVariantsRepository.findById(id).orElse(null);
    
        if (productVariant == null) {
            throw new RuntimeException("ProductVariant with ID " + id + " not found");
        }
        try{
            productVariant.setInCart(true);
            productVariantsRepository.save(productVariant);
            redisTemplate.getConnectionFactory().getConnection().flushAll();
            return true;
        }catch(Exception e){
            System.out.println("ERROR: Add to cart error--" + e);
            return false;
        }
        
    }
    
    @SuppressWarnings("deprecation")
    @CacheEvict(key="#id",value = "rem_cart", allEntries = true)
    public boolean rem_cart(Long id) {
        ProductVariants productVariant = productVariantsRepository.findById(id).orElse(null);
    
        if (productVariant == null) {
            throw new RuntimeException("ProductVariant with ID " + id + " not found");
        }
        try{
            productVariant.setInCart(false);
            productVariantsRepository.save(productVariant);
            redisTemplate.getConnectionFactory().getConnection().flushAll();
            return true;
        }catch(Exception e){
            System.out.println("ERROR: Add to cart error--" + e);
            return false;
        }
        
    }   

}
