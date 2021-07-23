/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.tools;

import lombok.Getter;

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
}
