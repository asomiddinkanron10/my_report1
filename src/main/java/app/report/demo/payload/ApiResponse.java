package app.report.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
    private Object object;
    private Integer page;
    private Long totalElements;

    private Integer newTime;
    private Double newPrice;

    public ApiResponse(String message, boolean success, Object object, Integer page, Long totalElements) {
        this.message = message;
        this.success = success;
        this.object = object;
        this.page = page;
        this.totalElements = totalElements;
    }

    public ApiResponse(Integer newTime, Double newPrice) {
        this.newTime = newTime;
        this.newPrice = newPrice;
    }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public ApiResponse(boolean success, Object object) {
        this.success = success;
        this.object = object;
    }

    public ApiResponse(Object object) {
        this.object = object;
    }
}
