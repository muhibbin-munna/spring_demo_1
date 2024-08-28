package com.example.project1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties("category")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    @SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1)
    private Long itemId;
    private String itemName;
    private Long price;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    @ManyToOne()
    @JoinColumn(name = "category_id")
//    @JsonBackReference
    private Category category;

}
