package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ClientService clientService = new ClientService();

		long clientId = clientService.create("John Doe");
		System.out.println("Created client with id: " + clientId);

		String clientName = clientService.getById(clientId);
		System.out.println("Client name: " + clientName);

		clientService.setName(clientId, "Jane Smith");
		System.out.println("Updated client name.");

		List<Client> allClients = clientService.listAll();
		System.out.println("All clients:");
		for (Client client : allClients) {
			System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
		}

		clientService.deleteById(clientId);
		System.out.println("Deleted client with id: " + clientId);
	}


}
