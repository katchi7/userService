package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Otp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
    public OtpDto(Otp otp){
        this(otp.getId(),otp.getOtpStr(),otp.getOtpExp());
    }
    @NotNull
    private String id;
    @NotNull
    @Size(min = 6,max = 6)
    private String otp;
    private LocalDateTime otpExp;

    public String getOtpExp() {
        return otpExp.toString();
    }
    public boolean isValid(){ return this.otpExp.isAfter(LocalDateTime.now() );
    }
    public Otp asOtp(){
        return new Otp(id,otp,otpExp);
    }
}
