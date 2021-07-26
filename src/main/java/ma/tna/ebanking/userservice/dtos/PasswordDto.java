package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotNull
    @Size(min=5,message = "oldPassword must be >5")
    private String oldPassword;
    @NotNull
    @Size(min=5,message = "newPassword must be >5")
    private String newPassword;
    @NotNull
    private Integer id;
}
