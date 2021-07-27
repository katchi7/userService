/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.tna.ebanking.userservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Auditable;
import ma.tna.ebanking.userservice.dtos.ModifiedField;
import org.apache.commons.beanutils.PropertyUtils;

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
public class HistoryService<T extends Auditable<T>> {

    
    @Value("${history.deepness:50}")
    private Integer historyDeepness;
    private List<T> historyList ;
    private Revisions<Integer, T> listRevisions = null;

    

    @Transactional(readOnly = true)
    public void findHistory(T selected,RevisionRepository<T, Integer, Integer> repo) {
        listRevisions = null;
        historyList = new ArrayList<>();
        
        try {
            listRevisions = repo.findRevisions((Integer) PropertyUtils.getProperty(selected, "id"));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(HistoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        List<ModifiedField> modifiedFieldList=null;
        for (Revision<Integer, T> revision : listRevisions) {
        	Auditable<T> audit=revision.getEntity();
        	audit.setRevisionNumber(revision.getRevisionNumber().orElse(0));
        	if(i>0)
        	{
        		modifiedFieldList =	audit.getModifiedFields(historyList.get(i-1));
        		audit.setModifiedFieldList(modifiedFieldList);
        	}
        	i++;
                    historyList.add(revision.getEntity());
        	
        }
        historyList.removeIf(audit -> audit.getModifiedFieldList() == null || audit.getModifiedFieldList().isEmpty());
        
    }
}
