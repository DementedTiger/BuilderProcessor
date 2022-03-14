package com.revature.annotation;

public class AnimalBuilder {

   private Animal object = new Animal();

   public Animal build() {
    return object;
    }

   public AnimalBuilder setName(java.lang.String value) {
    object.setName(value);
    return this;
   }

   public AnimalBuilder setLegs(int value) {
    object.setLegs(value);
    return this;
   }

}
