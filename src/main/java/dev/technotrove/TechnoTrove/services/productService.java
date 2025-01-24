package dev.technotrove.TechnoTrove.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import dev.technotrove.TechnoTrove.repositories.*;
import dev.technotrove.TechnoTrove.entities.*;

@Service
public class productService {
    @Autowired
    private ProductVariantsRepository productVariantRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @SuppressWarnings("deprecation")
    @Cacheable(value = "productInfo", key = "#prod_var_id")
    public productInfo get_prod_info(Long prod_var_id) {
        productInfo info = new productInfo();

        ProductVariants activeVariant = productVariantRepository.findById(prod_var_id)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found"));

                
        List<ProductVariants> allVariants = productVariantRepository.findAll();
        List<ProductVariants> variants = allVariants.stream()
                .filter(variant -> variant.getProduct_id().equals(activeVariant.getProduct_id()))
                .collect(Collectors.toList());

        info.setProd(activeVariant.getProduct_id());
        info.setVar1(variants.get(0));
        info.setVar2(variants.get(1));
        info.setVar3(variants.get(2));
        info.setActive_var(prod_var_id);
        redisTemplate.getConnectionFactory().getConnection().flushAll();

        return info;
    }

    

}


