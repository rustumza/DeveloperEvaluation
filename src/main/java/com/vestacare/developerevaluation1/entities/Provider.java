package com.vestacare.developerevaluation1.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rustu
 */
@Entity
@Table(name="provider")
@Data
@NoArgsConstructor
public class Provider implements Serializable,PersistentObject {
    
    //Default constructor created by Lombok.
    
    public Provider(String providerName,
            String taxId,String providerRefNum, String facility,
                String address1, String city, String state, String zip){
    
        this.providerName = providerName;
        this.taxId = taxId;
        this.providerRefNum = providerRefNum;
        this.facility = facility;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="provider_name")
    private String providerName;

    @Column(name="tax_id", unique = true)
    private String taxId;

    @Column(name="provider_ref_num")
    private String providerRefNum;

    @Column(name="facility")
    private String facility;

    @Column(name="address1")
    private String address1;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zip")
    private String zip;
   
    //Getters and Setters created by Lombok.
    
}
