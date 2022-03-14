package com.revature.annotation;

public class PersonBuilder {

   private Person object = new Person();

   public Person build() {
    return object;
    }

   public PersonBuilder setAge(int value) {
    object.setAge(value);
    return this;
   }

   public PersonBuilder setName(java.lang.String value) {
    object.setName(value);
    return this;
   }

}
