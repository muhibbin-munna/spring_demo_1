package com.example.project1.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_sequence")
    @SequenceGenerator(name = "cat_sequence", sequenceName = "cat_sequence", allocationSize = 1)
    private Long categoryId;
    private String categoryName;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "category"
    )
//    @JsonManagedReference
    private List<Item> items = new ArrayList<>();

}
