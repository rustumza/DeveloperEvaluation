package com.vestacare.developerevaluation1.persistence;

import com.vestacare.developerevaluation1.entities.Claim;
import java.util.List;


/**
 *
 * @author rustu
 */
public class ClaimDao extends GenericDao<Claim>{
    
    /**
     * Finds a Claim by claimNumber.
     * Returns null if the claim does not exist.
     *
     * @param claimNumber
     * @return provider     
     */
    public Claim findByClaimNumber(final String claimNumber){
        List<Claim> list = (List<Claim>)getEntityManager()
                        .createQuery("SELECT c FROM Claim c WHERE c.claimNumber = :claimNumber")
                            .setParameter("claimNumber", claimNumber)
                                .getResultList();
        return list.isEmpty()?null:list.get(0);
    }
    
    /**
     * Returns true if the Claim with claimNumber exists in the database or
     * false in any other case.
     *
     * @param claimNumber
     * @return int     
     */
    public boolean existClaimByClaimNumber(final String claimNumber){
       
        List list = (List) getEntityManager()
                        .createQuery("SELECT 1 FROM Claim c WHERE c.claimNumber = :claimNumber")
                            .setParameter("claimNumber", claimNumber)
                                .getResultList();        
        return !list.isEmpty();
    }
}
