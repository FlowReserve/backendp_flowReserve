package com.flowreserve.demo1.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity

public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "request_id") // Nombre de la columna en la tabla "request"
    private Request request;

    @ManyToOne
    @JoinColumn(name = "user_id") // Nombre de la columna en la tabla "request"
    private User user;


    private String filename;
    private String extension;
    private Date upload_date;




}
