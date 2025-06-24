package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Company {
    private String name;
    private String id;
    private transient List<Product> products;


}
