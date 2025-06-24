package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String Category_id;
    private Double price;
    private Integer count;
    private Measure measure;
    private String Company_id;

}
