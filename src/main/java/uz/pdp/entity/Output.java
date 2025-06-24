package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Output {
    private String id;
    private String date;
    private Double overallPrice;
    private String Shop_id;
    private List<OutputProduct> productList;
}
