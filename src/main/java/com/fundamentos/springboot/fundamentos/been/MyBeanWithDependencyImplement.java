package com.fundamentos.springboot.fundamentos.been;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

    MyOperation myOperation;
    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        int numero = 1;
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola desde la implementación de un bean con dependencia");
    }
}
