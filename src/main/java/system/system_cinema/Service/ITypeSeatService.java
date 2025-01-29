package system.system_cinema.Service;

import system.system_cinema.DTO.Request.TypeSeatRequest;
import system.system_cinema.DTO.Response.TypeSeatResponse;

import java.util.List;

public interface ITypeSeatService {
    List<TypeSeatResponse> getAllTypeSeats();

    TypeSeatResponse createTypeSeat(TypeSeatRequest typeSeatRequest);

    TypeSeatResponse updateTypeSeat(String id, TypeSeatRequest request);

    void deleteTypeSeat(String id);
}
