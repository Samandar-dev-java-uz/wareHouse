package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Input {

    private String id;
    private String date;
    private Double overallPrice;
    private String  Company_Id;
    private List<InoutProduct> productList;

}
