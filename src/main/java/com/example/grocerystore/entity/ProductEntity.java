package com.example.grocerystore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Table(name = "product")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Long productId = 0L;
    @Column
    String name;
    @Column(name = "unit_format")
    String unitFormat;
    @Column(name = "img_path")
    String imgPath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    CategoryEntity categoryEntity;


    @OneToMany(mappedBy = "productEntity")
    private List<InventoryEntity> inventoryEntityList;
    @Override
    public String toString() {
        return "ProductionEntity{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", unitFormat='" + unitFormat + '\'' +
                ", imgPath='" + imgPath + '\'' +
                //  ", categoryEntity=" + (categoryEntity != null ? categoryEntity.toString() : "null") +
                '}';
    }

}

