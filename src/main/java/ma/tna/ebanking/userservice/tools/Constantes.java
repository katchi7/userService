/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.tools;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Constantes {
       private Constantes(){super();}
       @Getter
       protected static final String IMAGE_FIELD = "image";
       @Getter
       protected static final String USER_NOT_FOUND = "User does not exist";
       @Getter
       protected static final String CUSTOMER = "customer";
       @Getter
       protected static final String KEY = "key";
       @Getter
       protected static final String ID = "customerId";
       @Getter
       protected static final Integer OTP_EXPIRATION_TIME = 5;
       @Getter
       public static final String CUSTOMER_INFO_HYSTRIX_TIMEOUT="6000";
       @Getter
       protected static final String T24_RETOUR_ATTR="Retour";
       @Getter
       protected static final String T24_SUCCESS_CODE="000";
       @Getter
       protected static final HttpStatus USER_NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;//404
       @Getter
       protected static final HttpStatus USER_PASSWORD_DOES_NOT_MATCH = HttpStatus.NOT_ACCEPTABLE;//406
       @Getter
       protected static final HttpStatus USER_NEW_MATCH_OLD = HttpStatus.BAD_REQUEST;//400
       @Getter
       protected static final HttpStatus USER_PASSWORD_VALIDATION_STATUS = HttpStatus.UNAUTHORIZED;//401



}
