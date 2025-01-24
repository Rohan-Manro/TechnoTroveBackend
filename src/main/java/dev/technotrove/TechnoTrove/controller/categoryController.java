package dev.technotrove.TechnoTrove.controller;

import java.util.List;
import dev.technotrove.TechnoTrove.entities.*;
import dev.technotrove.TechnoTrove.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class categoryController {
    @Autowired
    categoryService catSer;

    @GetMapping("")
    public List<ProductVariants> getAllProds(){
        return catSer.get_all_prods();
    }

    @GetMapping("{category}")
    public List<ProductVariants> getCatProds(@PathVariable String category){
        return catSer.get_cat_prods(category);
    }

    @GetMapping("favorites")
    public List<ProductVariants> getFavs(){
        return catSer.get_favs();
    }   

    @RequestMapping(path="favorites/{prod_var_id}",method=RequestMethod.PATCH)
    public ResponseEntity<String> toggleFav(@PathVariable Long prod_var_id) {
        try {
            boolean isFavorite = catSer.toggle_fav(prod_var_id);
            String message = isFavorite
                    ? "Product added to favorites."
                    : "Product removed from favorites.";
            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("cart")
    public List<ProductVariants> getCart(){
        return catSer.get_cart();
    } 

    @RequestMapping(path="cart/add/{prod_var_id}",method=RequestMethod.PATCH)
    public ResponseEntity<String> addCart(@PathVariable Long prod_var_id) {
        try {
            boolean isFavorite = catSer.add_cart(prod_var_id);
            String message = isFavorite
                    ? "Product added to the cart."
                    : "Unable to add to the cart.";
            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }  
    
    @RequestMapping(path="cart/remove/{prod_var_id}",method=RequestMethod.PATCH)
    public ResponseEntity<String> remCart(@PathVariable Long prod_var_id) {
        try {
            boolean isFavorite = catSer.rem_cart(prod_var_id);
            String message = isFavorite
                    ? "Product removed from the cart."
                    : "Unable to remove from the cart.";
            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    } 
}
