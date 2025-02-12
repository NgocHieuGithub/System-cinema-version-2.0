package system.system_cinema.dto.response;

import lombok.Data;

@Data
public class TypeSeatResponse {
    private String id;
    private String typeName; // Tên loại ghế (thêm mới)
    private int price;       // Giá loại ghế
}
