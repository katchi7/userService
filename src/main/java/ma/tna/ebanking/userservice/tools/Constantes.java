/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.tools;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author AnasAfif
 */
public class Constantes {
       private Constantes(){super();}
       @Getter
       protected static final List<String> EXCLUDED_FIELDS = Arrays.asList("revisionNumber", "modifiedFieldList", "modifiedDate","modifiedBy");
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
}
