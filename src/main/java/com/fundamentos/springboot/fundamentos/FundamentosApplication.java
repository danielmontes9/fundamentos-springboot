package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.been.MyBean;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private MyBean myBean;
	private ComponentDependency componentDependency;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository) {
		this.componentDependency = componentDependency;

		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;

		this.myBeanWithProperties = myBeanWithProperties;

		this.userPojo = userPojo;

		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//previousExamples();
		saveUsersInDataBase();
	}


	private void saveUsersInDataBase() {
		User user1 = new User("user1", "user1@domain.com", LocalDate.of(2021, 03, 12));
		User user2 = new User("user2", "user2@domain.com", LocalDate.of(2021, 04, 22));
		User user3 = new User("user3", "user3@domain.com", LocalDate.of(2021, 05, 9));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2021, 06, 11));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021, 07, 28));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021, 8,11));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021, 9, 10));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021, 10, 23));
		User user9 = new User("user9", "user9@domain.com", LocalDate.of(2021, 11, 30));
		User user10 = new User("user10", "user10@domain.com", LocalDate.now());

		List<User> list = Arrays.asList(user1,user2, user3, user4, user5, user6, user7, user8, user9, user10);
		list.stream().forEach(userRepository::save);
	}

	private void previousExamples() {
		componentDependency.saludar();

		myBean.print();
		myBeanWithDependency.printWithDependency();

		System.out.println(myBeanWithProperties.function());

		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());

		LOGGER.error("Esto es un error generado a partir de la aplicacion");
	}
}
