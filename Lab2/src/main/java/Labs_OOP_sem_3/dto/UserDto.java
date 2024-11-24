package Labs_OOP_sem_3.dto;

import lombok.*;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    Integer id;
    String name;
    String password;
}
