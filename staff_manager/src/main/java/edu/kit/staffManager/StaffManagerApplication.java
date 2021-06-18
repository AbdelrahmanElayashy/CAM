package edu.kit.staffManager;

import edu.kit.staffManager.api.dto.UserDTO;
import edu.kit.staffManager.logic.StaffManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class StaffManagerApplication implements CommandLineRunner {

	private final StaffManager staffManager;

	@Autowired
	public StaffManagerApplication(StaffManager staffManager) {
		this.staffManager = staffManager;
	}

	public static void main(String[] args) {
		SpringApplication.run(StaffManagerApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		staffManager.addSuperUser();

		//Creates technical and medical staff with three users
		try {
			staffManager.addType("TECHNICAL");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addType("MEDICAL");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addRank("TECHNICAL", "ADMIN");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addRank("TECHNICAL", "NORMAL");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addRank("MEDICAL", "NORMAL");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addUser(new UserDTO(UUID.fromString("702cc8e9-85f5-467b-9ef7-8afe0d09cea2"), "medicalNormal", "0000", "MEDICAL", "NORMAL"));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addUser(new UserDTO(UUID.fromString("bded9fc9-2aa2-415f-ac21-5f7605948404"), "technicalNormal", "1234", "TECHNICAL", "NORMAL"));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		try {
			staffManager.addUser(new UserDTO(UUID.fromString("0a62fff2-ca13-41d9-9af6-11c3abf33a1c"), "technicalAdmin", "4567", "TECHNICAL", "ADMIN"));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
