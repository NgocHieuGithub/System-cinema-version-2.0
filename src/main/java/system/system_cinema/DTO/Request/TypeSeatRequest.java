package system.system_cinema.DTO.Request;

import lombok.Data;

@Data
public class TypeSeatRequest {
    private String typeName; // Tên loại ghế (thêm mới)
    private int price;       // Giá loại ghế
}
