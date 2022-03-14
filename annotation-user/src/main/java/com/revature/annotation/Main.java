package com.revature.annotation;

public class Main {
    public static void main(String[] args){
        Person person = new com.revature.annotation.PersonBuilder()
                .setAge(44)
                .setName("Frankie")
                .build();

        Animal animal = new com.revature.annotation.AnimalBuilder()
                .setName("Tiger")
                .setLegs(4)
                .build();

        System.out.println(person);
        System.out.println(animal);
    }
}
