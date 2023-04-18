package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.been.MyBean;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private MyBean myBean;
	private ComponentDependency componentDependency;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo) {
		this.componentDependency = componentDependency;

		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;

		this.myBeanWithProperties = myBeanWithProperties;

		this.userPojo = userPojo;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		componentDependency.saludar();

		myBean.print();
		myBeanWithDependency.printWithDependency();

		System.out.println(myBeanWithProperties.function());

		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());

		LOGGER.error("Esto es un error generado a partir de la aplicacion");
	}
}
