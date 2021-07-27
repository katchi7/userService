package ma.tna.ebanking.userservice.services;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.model.Otp;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Random;
@Log4j2
@Service
public class OtpService {

    private final CustomerRepo customerRepo;
    private final Random random;
    public OtpService(CustomerRepo customerRepo, Random random) {
        this.customerRepo = customerRepo;
        this.random = random;
    }

    /**
     * This methode is responsible for generating customer OTP
     * @param userId customer id
     * @return Otp : an object containing informations about the created OTP
     * @throws NoSuchElementException if the user does not exist
     */
    public Otp createOtp(int userId){
        int otpInt = 100000 + random.nextInt(800000);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(Constantes.getOTP_EXPIRATION_TIME());
        Otp otp = new Otp(userId,""+otpInt,localDateTime);
        int updated = customerRepo.saveOtp(otp.getOtpStr(),otp.getOtpExp(),otp.getId());
        if(updated > 0) return otp;
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * this methode is responsible for loading customer OTP
     * @param userId user's id
     * @return Otp object containing customer's otp data
     * @throws NoSuchElementException if the customer does not exist
     */
    public Otp getUserOtp(int userId){
        Otp otp = customerRepo.findCustomerOtp(userId);
        if(otp!=null) return otp;
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * This methode is responsible for validating the customer otp, it receives an otp as a string, customer email,
     * if received otp is matching customer's otp it returns otp data
     * else it throws an InvalidParameterException
     * @param id customer id
     * @param otpStr otp sent by the user
     * @return Otp object containing otp data
     * @throws InvalidParameterException if the otp is not matching
     * @throws  NoSuchElementException if the customer does not exist
     */
    public Otp validateOtp(int id,String otpStr){
        Otp otp = customerRepo.findCustomerOtp(id);
        if(otp!=null){
            if(otpStr.equals(otp.getOtpStr())){
                return otp;
            }
            else
                throw new InvalidParameterException("Otp is not valid");
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }
}
