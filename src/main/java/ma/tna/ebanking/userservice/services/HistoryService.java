/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Auditable;
import ma.tna.ebanking.userservice.model.ModifiedField;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author AFIF Anas
 */

@Data @AllArgsConstructor @NoArgsConstructor 
@Component("test")
@Scope("prototype")
public class HistoryService<T> {

    
    @Value("${history.deepness:50}")
    private Integer Historydeepness;
    private List<T> historyList ;
    private Revisions<Integer, T> listRevisions = null;

    

    @Transactional(readOnly = true)
    public void findHistory(T selected,RevisionRepository<T, Integer, Integer> repo) {
        listRevisions = null;
        historyList = new ArrayList();
        List<T> historyListTemp = new ArrayList();
        
        try {
            listRevisions = repo.findRevisions((Integer) PropertyUtils.getProperty(selected, "id"));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        List<ModifiedField> modifiedFieldList=null;
        for (Revision<Integer, T> revision : listRevisions) {
        	Auditable audit=(Auditable) revision.getEntity();
        	audit.setRevisionNumber(revision.getRevisionNumber().orElse(0));
        	modifiedFieldList=null;
        	if(i>0)
        	{
        		modifiedFieldList =	audit.getModifiedFields(historyList.get(i-1));
//        		modifiedFieldList =	audit.getModifiedFields(historyListTemp.get(i-1));
        		audit.setModifiedFieldList(modifiedFieldList);
        	}
        	i++;
//                if(modifiedFieldList !=null && modifiedFieldList.size()>0){
                    historyList.add(revision.getEntity());
//                }
//                    historyListTemp.add(revision.getEntity());
        	
        }
        for (Iterator<T> iterator = historyList.iterator(); iterator.hasNext();) {
            Auditable audit=(Auditable) iterator.next();
            if(audit.getModifiedFieldList()==null || audit.getModifiedFieldList().isEmpty()) iterator.remove();
            
        }
        
    }
}
