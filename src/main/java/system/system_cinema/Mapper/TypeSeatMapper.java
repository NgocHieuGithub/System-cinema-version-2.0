package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.TypeSeatRequest;
import system.system_cinema.DTO.Response.TypeSeatResponse;
import system.system_cinema.Model.TypeSeat;

@Component
public class TypeSeatMapper {
    public TypeSeatResponse toTypeSeatResponse(TypeSeat typeSeat) {
        TypeSeatResponse response = new TypeSeatResponse();
        response.setId(typeSeat.getId());
        response.setTypeName(typeSeat.getTypeName()); // Map typeName
        response.setPrice(typeSeat.getPrice());
        return response;
    }

    public TypeSeat toTypeSeat(TypeSeatRequest request) {
        return TypeSeat.builder()
                .typeName(request.getTypeName()) // Map typeName
                .price(request.getPrice())
                .build();
    }
}
