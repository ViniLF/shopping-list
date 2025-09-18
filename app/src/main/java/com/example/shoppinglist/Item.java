package com.example.shoppinglist;

public class Item {
    private String name;
    private long id;

    // Construtor principal
    public Item(String name) {
        this.name = name;
        this.id = System.currentTimeMillis(); // ID único baseado no timestamp
    }

    // Construtor com ID (para casos especiais)
    public Item(String name, long id) {
        this.name = name;
        this.id = id;
    }

    // Getters
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    // toString para debug
    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}