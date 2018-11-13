package com.vestacare.developerevaluation1.entities;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rustu
 */
@Entity
@Table(name = "claim")
@Data
@NoArgsConstructor
public class Claim implements Serializable, PersistentObject {    

    //Default constructor created by Lombok.
    
    public Claim(String claimNumber, String clientClaim,
            String patient,Provider provider){
        this.claimNumber = claimNumber;
        this.clientClaim = clientClaim;
        this.patient = patient;
        this.provider = provider;    
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="claim_number", unique = true)
    private String claimNumber;    
    
    @Column(name="cliente_claim")
    private String clientClaim;

    @Column(name="patient")
    private String patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="provider_id")
    private Provider provider;

    //Getters and Setters created by Lombok.
}
