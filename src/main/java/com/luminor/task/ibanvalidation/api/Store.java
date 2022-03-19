package com.luminor.task.ibanvalidation.api;

public class Store {
    private final Item item;

    public Store(Item item) {
        this.item = item;
        testInterface();
    }
    public void testInterface(){
        System.out.println(item.getName());
    }

    public String getName(){
        return item.getName();
    }
}
