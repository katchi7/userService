package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    @Min(1)
    private String userName;
    @NotNull
    @Size(min = 3)
    private String password;

}
