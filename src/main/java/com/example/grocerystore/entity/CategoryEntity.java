package com.example.grocerystore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name="category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId=0L;
    @Column
    private String name;

    @OneToMany(mappedBy = "categoryEntity",fetch = FetchType.LAZY)
    List<ProductEntity> productEntityList;
    @Override
    public String toString() {
        return "CategoryEntity{" +
                "Category_Id=" + categoryId +
                ", name='" + name + '\'' +
                ", productionEntityList=" + "productionEntityList" +
                '}';
    }
}