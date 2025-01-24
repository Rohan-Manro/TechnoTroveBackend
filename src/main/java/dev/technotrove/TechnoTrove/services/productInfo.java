package dev.technotrove.TechnoTrove.services;

import dev.technotrove.TechnoTrove.entities.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class productInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product prod;
    private ProductVariants var1;
    private ProductVariants var2;
    private ProductVariants var3;
    private Long active_var;
}
