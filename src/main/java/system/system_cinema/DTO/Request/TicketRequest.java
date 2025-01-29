package system.system_cinema.DTO.Request;

import lombok.Data;

@Data
public class TicketRequest {
    private int price;               // Giá vé
    private boolean isPaid;          // Trạng thái thanh toán
    private String userId;           // ID người dùng
    private String showtimeId;       // ID suất chiếu
}
