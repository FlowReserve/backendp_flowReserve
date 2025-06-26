package com.flowreserve.demo1.model.EtapaRequest;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class EtapaRequestModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}
