package com.example.faikyoussef.config;

import com.example.faikyoussef.entity.*;
import com.example.faikyoussef.repository.ClientRepository;
import com.example.faikyoussef.repository.CreditRepository;
import com.example.faikyoussef.repository.RemboursementRepository;
import com.example.faikyoussef.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Date;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            // Initialize roles if they don't exist
            if (roleRepository.count() == 0) {
                System.out.println("Initializing roles...");
                
                Role clientRole = new Role(ERole.ROLE_CLIENT);
                Role employeRole = new Role(ERole.ROLE_EMPLOYE);
                Role adminRole = new Role(ERole.ROLE_ADMIN);
                
                roleRepository.save(clientRole);
                roleRepository.save(employeRole);
                roleRepository.save(adminRole);
                
                System.out.println("Roles initialized successfully.");
            }
        };
    }

    @Bean
    @DependsOn("initRoles")
    CommandLineRunner initDatabase(ClientRepository clientRepository,
                                   CreditRepository creditRepository,
                                   RemboursementRepository remboursementRepository) {        return args -> {
            // Create Clients
            Client client1 = new Client();
            client1.setName("John Doe");
            client1.setEmail("john.doe@example.com");
            
            Client client2 = new Client();
            client2.setName("Jane Smith");
            client2.setEmail("jane.smith@example.com");
            
            clientRepository.save(client1);
            System.out.println("Saved Client: " + client1);
            clientRepository.save(client2);
            System.out.println("Saved Client: " + client2);

            // Create Credits
            CreditPersonnel creditP1 = new CreditPersonnel("Personal Loan for Home Renovation");
            creditP1.setAmount(10000);
            creditP1.setDate(new Date());
            creditP1.setStatus("Approved");
            creditP1.setClient(client1);
            creditRepository.save(creditP1);
            System.out.println("Saved Credit: " + creditP1);

            CreditImmobilier creditI1 = new CreditImmobilier("Apartment");
            creditI1.setAmount(150000);
            creditI1.setDate(new Date());
            creditI1.setStatus("Pending");
            creditI1.setClient(client2);
            creditRepository.save(creditI1);
            System.out.println("Saved Credit: " + creditI1);

            CreditProfessionnel creditPro1 = new CreditProfessionnel("Business Expansion", "Doe Corp");
            creditPro1.setAmount(50000);
            creditPro1.setDate(new Date());
            creditPro1.setStatus("Approved");
            creditPro1.setClient(client1);
            creditRepository.save(creditPro1);
            System.out.println("Saved Credit: " + creditPro1);

            // Create Remboursements
            Remboursement r1 = new Remboursement(null, new Date(), 500, "Monthly Installment", creditP1);
            Remboursement r2 = new Remboursement(null, new Date(), 1000, "Initial Payment", creditI1);
            remboursementRepository.save(r1);
            System.out.println("Saved Remboursement: " + r1);
            remboursementRepository.save(r2);
            System.out.println("Saved Remboursement: " + r2);

            // Verify data insertion by retrieving and logging entities
            System.out.println("\n--- Verifying Data Insertion ---");
            System.out.println("Clients found with findAll():");
            System.out.println("-------------------------------");
            clientRepository.findAll().forEach(client -> System.out.println(client));

            System.out.println("\nCredits found with findAll():");
            System.out.println("------------------------------");
            creditRepository.findAll().forEach(credit -> System.out.println(credit));

            System.out.println("\nRemboursements found with findAll():");
            System.out.println("-----------------------------------");
            remboursementRepository.findAll().forEach(remboursement -> System.out.println(remboursement));
            System.out.println("\n--- Data Verification Complete ---");
        };
    }
}
