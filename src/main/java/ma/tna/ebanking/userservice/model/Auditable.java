/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.persistence.Transient;
import lombok.Data;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.LazyInitializationException;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/**
 *
 * @author AnasAfif
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Audited
@Data
//@JsonView(HistoryProjection.class)
public abstract class Auditable<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    protected String createdBy;
    
    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date createdDate;
    
    @LastModifiedBy
    protected String modifiedBy;
    
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date modifiedDate;

    @Transient
    private List<ModifiedField> modifiedFieldList;


    @Transient
    private Integer revisionNumber;

    
   
    public List<ModifiedField> getModifiedFields(T oldObject) {

        List<ModifiedField> modifiedFieldsList;
        modifiedFieldsList = new ArrayList<>();
        Class<?> classe = oldObject.getClass();
        Field[] fields = (Field[]) ArrayUtils.addAll(classe.getDeclaredFields(), classe.getSuperclass().getDeclaredFields());
        return registerModifiedFields(modifiedFieldsList,fields,oldObject);
    }

    private List<ModifiedField> registerModifiedFields(List<ModifiedField> modifiedFieldsList,Field[] fields,T oldObject){
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormatCal = new SimpleDateFormat("HH:mm:ss");
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object oldObj = field.get(oldObject);
                Object newObj = field.get(this);
                String oldValue = oldObj != null ? oldObj.toString() : "";
                String newValue = newObj != null ? newObj.toString() : "";

                    if ((newObj instanceof Date)) {
                        newValue = simpleDateFormat.format((Date) newObj);
                    }
                    if ((oldObj instanceof Date)) {
                        oldValue = simpleDateFormat.format((Date) oldObj);
                    }
                    if ((newObj instanceof Calendar)) {
                        newValue = simpleDateFormatCal.format(((Calendar) newObj).getTime());
                    }
                    if ((oldObj instanceof Calendar)) {
                        oldValue = simpleDateFormatCal.format(((Calendar) oldObj).getTime());
                    }
                if (!Constantes.EXCLUDED_FIELDS.contains(field.getName()) && !oldValue.equals(newValue)) {
                    try {
                        modifiedFieldsList.add(new ModifiedField(ResourceBundle.getBundle("bundle").getString("Create" + oldObject.getClass().getSimpleName() + "Label_" + field.getName()), oldValue, newValue));
                    } catch (Exception e1) {
                        modifiedFieldsList.add(new ModifiedField(field.getName(), oldValue, newValue));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException | LazyInitializationException ex) {
                Logger.getLogger(Auditable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modifiedFieldsList;
    }
    public void clone(Auditable<T> auditable) {
            auditable.setCreatedBy(this.createdBy);
            auditable.setCreatedDate(this.createdDate);
            auditable.setModifiedBy(this.modifiedBy);
            auditable.setModifiedDate(this.modifiedDate);
    }

    public String getModifiedDate() {
        return DateTimeFormat.forPattern("dd/MM/yyyy:hh:mm:ss").print(new DateTime(modifiedDate));
    }
}
