package com.fundamentos.springboot.fundamentos.been;

public class MyBeanImplement implements MyBean {

    @Override
    public void print() {
        System.out.println("Hola desde mi propia implementacion del bean");
    }
}
