package com.anastasiia.entity;

import java.io.Serializable;

/**
 * Root of all entities that have identifier field
 * @author Anastasiia Formahei
 * */

public class Entity implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
