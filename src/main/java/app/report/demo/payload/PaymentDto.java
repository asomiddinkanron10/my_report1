package app.report.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private UUID id;
    private UUID categoryId;
    private CategoryDto categoryDto;
    private UUID userId;
    private UserDto userDto;
    private Double price;
    private String description;

    public PaymentDto(UUID id, UUID categoryId, UUID userId, Double price, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.userId = userId;
        this.price = price;
        this.description = description;
    }
}
