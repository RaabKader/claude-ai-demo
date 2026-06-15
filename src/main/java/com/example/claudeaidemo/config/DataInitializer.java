package com.example.claudeaidemo.config;

import com.example.claudeaidemo.entity.Address;
import com.example.claudeaidemo.entity.JobFunction;
import com.example.claudeaidemo.entity.Person;
import com.example.claudeaidemo.repository.AddressRepository;
import com.example.claudeaidemo.repository.JobFunctionRepository;
import com.example.claudeaidemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the in-memory H2 database with reference data and 100 persons on startup.
 * <p>
 * The H2 database uses {@code ddl-auto=create-drop}, so it is empty on every boot.
 * Generation is deterministic (no randomness) so the dataset is stable across restarts,
 * and the runner is a no-op if persons already exist (idempotent / safe to re-run).
 */
@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private static final int PERSON_COUNT = 100;

    private static final String[] FIRST_NAMES = {
            "Alice", "Bruno", "Chloé", "David", "Emma", "Fabien", "Garance", "Hugo",
            "Inès", "Julien", "Karim", "Léa", "Maxime", "Nadia", "Olivier", "Pauline",
            "Quentin", "Rachel", "Samuel", "Théa"
    };

    private static final String[] LAST_NAMES = {
            "Martin", "Bernard", "Dubois", "Thomas", "Robert", "Richard", "Petit", "Durand",
            "Leroy", "Moreau", "Simon", "Laurent", "Lefebvre", "Michel", "Garcia", "David",
            "Bertrand", "Roux", "Vincent", "Fournier"
    };

    /** City / zip / country tuples used to build the address pool. */
    private static final String[][] CITIES = {
            {"Paris", "75001", "France"},
            {"Lyon", "69001", "France"},
            {"Marseille", "13001", "France"},
            {"Toulouse", "31000", "France"},
            {"Bordeaux", "33000", "France"},
            {"Lille", "59000", "France"},
            {"Nantes", "44000", "France"},
            {"Brussels", "1000", "Belgium"},
            {"Geneva", "1201", "Switzerland"},
            {"Berlin", "10115", "Germany"},
            {"Madrid", "28001", "Spain"},
            {"Milan", "20100", "Italy"}
    };

    private static final String[] STREETS = {
            "Rue de la République", "Avenue des Champs", "Boulevard Voltaire",
            "Rue du Commerce", "Place de la Gare", "Allée des Tilleuls",
            "Chemin des Vignes", "Rue Victor Hugo"
    };

    private static final String[][] FUNCTIONS = {
            {"Software Engineer", "Designs, builds and maintains software systems"},
            {"Product Manager", "Owns the product roadmap and priorities"},
            {"QA Engineer", "Ensures product quality through testing"},
            {"DevOps Engineer", "Manages CI/CD pipelines and infrastructure"},
            {"Data Analyst", "Analyses data to support decision making"},
            {"UX Designer", "Designs user experiences and interfaces"},
            {"Scrum Master", "Facilitates agile ceremonies and removes blockers"},
            {"Tech Lead", "Provides technical direction for the team"},
            {"HR Manager", "Manages recruitment and people operations"},
            {"Sales Representative", "Drives revenue through client relationships"}
    };

    @Bean
    @Transactional
    CommandLineRunner seedDatabase(PersonRepository personRepository,
                                   AddressRepository addressRepository,
                                   JobFunctionRepository jobFunctionRepository) {
        return args -> {
            if (personRepository.count() > 0) {
                log.info("Database already populated ({} persons) — skipping seed.", personRepository.count());
                return;
            }

            List<JobFunction> functions = seedFunctions(jobFunctionRepository);
            List<Address> addresses = seedAddresses(addressRepository);
            seedPersons(personRepository, addresses, functions);

            log.info("Seeded database: {} functions, {} addresses, {} persons.",
                    functions.size(), addresses.size(), personRepository.count());
        };
    }

    private List<JobFunction> seedFunctions(JobFunctionRepository repository) {
        List<JobFunction> functions = new ArrayList<>();
        for (String[] f : FUNCTIONS) {
            JobFunction function = new JobFunction();
            function.setLabel(f[0]);
            function.setDescription(f[1]);
            functions.add(function);
        }
        return repository.saveAll(functions);
    }

    private List<Address> seedAddresses(AddressRepository repository) {
        List<Address> addresses = new ArrayList<>();
        // One address per city, per street → a varied pool to spread persons across.
        for (int i = 0; i < CITIES.length; i++) {
            String[] city = CITIES[i];
            String street = (10 + i) + " " + STREETS[i % STREETS.length];
            Address address = new Address();
            address.setStreet(street);
            address.setCity(city[0]);
            address.setZipCode(city[1]);
            address.setCountry(city[2]);
            addresses.add(address);
        }
        return repository.saveAll(addresses);
    }

    private void seedPersons(PersonRepository repository, List<Address> addresses, List<JobFunction> functions) {
        List<Person> persons = new ArrayList<>(PERSON_COUNT);
        for (int i = 0; i < PERSON_COUNT; i++) {
            String firstName = FIRST_NAMES[i % FIRST_NAMES.length];
            String lastName = LAST_NAMES[(i / FIRST_NAMES.length + i) % LAST_NAMES.length];

            Person person = new Person();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            // Index keeps emails unique even when first/last names repeat.
            person.setEmail(String.format("%s.%s%d@example.com",
                    toEmailToken(firstName), toEmailToken(lastName), i + 1));
            person.setPhone(String.format("+33 6 %02d %02d %02d %02d",
                    i % 100, (i * 3) % 100, (i * 7) % 100, (i * 11) % 100));
            person.setAddress(addresses.get(i % addresses.size()));
            person.setFunction(functions.get(i % functions.size()));
            persons.add(person);
        }
        repository.saveAll(persons);
    }

    /** Lower-cases a name and strips diacritics so it is safe for an email local-part. */
    private static String toEmailToken(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return normalized.toLowerCase();
    }
}
