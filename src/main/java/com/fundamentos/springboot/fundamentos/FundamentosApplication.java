package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.been.MyBean;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.been.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

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
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService) {
		this.componentDependency = componentDependency;

		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;

		this.myBeanWithProperties = myBeanWithProperties;

		this.userPojo = userPojo;

		this.userRepository = userRepository;

		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//previousExamples();
		saveUsersInDataBase();
		//getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional() {
		User test1 = new User("TestTransactional1", "testTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "testTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "testTransactional3@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "testTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try {
			userService.saveTransactional(users);
		} catch (Exception e) {
			LOGGER.error("Esta es una exception dentro del metodo transaccional");
		}

		userService.getAllUsers()
					.stream()
					.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional" +  user));
	}

		private  void getInformationJpqlFromUser() {
			LOGGER.info("Usuario con el metodo findByUserEmail" +
					userRepository.findByUserEmail("user1@domain.com")
							.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

			userRepository.findAndSort("user", Sort.by("id").descending())
					.stream()
					.forEach(user -> LOGGER.info("Usuario con metodo sort " + user));

			userRepository.findByName("user3")
					.stream()
					.forEach(user -> LOGGER.info("User con query method" + user));

			LOGGER.info("Usuario con query method findByEmailAndName: " + userRepository.findByEmailAndName("user5@domain.com", "user5")
					.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

			userRepository.findByNameLike("%p%")
					.stream()
					.forEach(user -> LOGGER.info("Usuario findByNameLike" + user));

			userRepository.findByNameOrEmail(null, "user10@domain.com")
					.stream()
					.forEach(user -> LOGGER.info("Usuario findByNameOrEmail" + user));

			userRepository.findByBirthDateBetween(LocalDate.of(2021, 01, 01), LocalDate.of(2023,12,31))
					.stream()
					.forEach(user -> LOGGER.info("Usuario entre el intervalo de fechas 2021 - 2023: " + user));

			userRepository.findByNameLikeOrderByIdDesc("%user%")
					.stream()
					.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado DES" + user));

			userRepository.findByNameContainingOrderByIdAsc("user")
					.stream()
					.forEach(user -> LOGGER.info("Usuario encontrado con containing y ordenado ASC" + user));

			LOGGER.info("El usuario a partir del named parameter es: " +
					userRepository.getAllByBirthdateAndEmail(LocalDate.of(2027, 9, 10), "user7@domain.com")
							.orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));
		}


		private void saveUsersInDataBase() {
			User user1 = new User("pedro", "user1@domain.com", LocalDate.of(2021, 03, 12));
			User user2 = new User("user2", "user2@domain.com", LocalDate.of(2022, 04, 22));
			User user3 = new User("user3", "user3@domain.com", LocalDate.of(2023, 05, 9));
			User user4 = new User("user4", "user4@domain.com", LocalDate.of(2024, 06, 11));
			User user5 = new User("user5", "user5@domain.com", LocalDate.of(2025, 07, 28));
			User user6 = new User("user6", "user6@domain.com", LocalDate.of(2026, 8,11));
			User user7 = new User("user7", "user7@domain.com", LocalDate.of(2027, 9, 10));
			User user8 = new User("user8", "user8@domain.com", LocalDate.of(2028, 10, 23));
			User user9 = new User("user9", "user9@domain.com", LocalDate.of(2029, 11, 30));
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
