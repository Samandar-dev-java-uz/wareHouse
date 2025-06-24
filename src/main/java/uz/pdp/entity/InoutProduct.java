package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InoutProduct {
    private String id;
    private String Product_id;
    private  Integer block;
    private Integer countEachBlock;
}
