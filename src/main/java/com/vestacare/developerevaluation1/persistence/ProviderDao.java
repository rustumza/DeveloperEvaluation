package com.vestacare.developerevaluation1.persistence;

import com.vestacare.developerevaluation1.entities.Provider;
import java.util.List;

/**
 *
 * @author rustu
 */
public class ProviderDao extends GenericDao<Provider>{
    
    /**
     * Finds the Provider by TaxId.
     * Returns null if the Provider does not exist.
     *
     * @param taxId
     * @return provider
     */
    public Provider findByTaxId(final String taxId){        
        List<Provider> list = (List<Provider>)getEntityManager()
                       .createQuery("SELECT p FROM Provider p WHERE p.taxId = :taxId")
                            .setParameter("taxId", taxId)
                                .getResultList();
        return list.isEmpty()?null:list.get(0);
    }
    
}
