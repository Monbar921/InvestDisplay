package ru.invest.display.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity <T extends Serializable>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
}
