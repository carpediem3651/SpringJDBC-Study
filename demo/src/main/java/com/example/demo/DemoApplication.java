package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	RoleDao roleDao;

	@java.lang.Override
	public void run(java.lang.String... args) throws Exception {
//		Role role = new Role();
//		role.setRoleId(3);
//		role.setName('ROLE_TEST');
//		roleDao.addRole(role);

//		boolean flag = roleDao.deleteRole(3);
//		System.out.println("flag : " + flag);

		Role role = roleDao.getRole(1);
		System.out.println(role.getRoleId());
	}
}
