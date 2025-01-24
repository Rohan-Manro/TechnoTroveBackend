package dev.technotrove.TechnoTrove.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class ProductVariants implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sku_id;

    private String name;
    private String price;

    @ManyToOne
    private Product product_id;

    private String image1_url;
    private String image2_url;
    private String image3_url;

    private int quantity;

    @Column(columnDefinition = "boolean default false")
    private boolean favorite;

    @Column(columnDefinition = "boolean default false")
    private boolean inCart;
}
