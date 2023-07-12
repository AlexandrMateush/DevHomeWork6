package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private List<Client> clients;
    private long nextId;

    public ClientService() {
        clients = new ArrayList<>();
        nextId = 1;
    }

    public long create(String name) {
        validateName(name);
        long id = nextId++;
        Client client = new Client(id, name);
        clients.add(client);
        return id;
    }

    public String getById(long id) {
        Client client = findClientById(id);
        return client.getName();
    }

    public void setName(long id, String name) {
        validateName(name);
        Client client = findClientById(id);
        client.setName(name);
    }

    public void deleteById(long id) {
        Client client = findClientById(id);
        clients.remove(client);
    }

    public List<Client> listAll() {
        return new ArrayList<>(clients);
    }

    private Client findClientById(long id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        throw new IllegalArgumentException("Client with id " + id + " does not exist.");
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid client name.");
        }
    }
}

