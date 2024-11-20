import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_3";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Scanner s = new Scanner(System.in);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println("Name: "+user.getFirstName()));

        //adding a new driver to the database
        System.out.println("Please provide the amount of drivers to be added: ");
        byte numberDrivers = s.nextByte();

        for (int i = 0; i < numberDrivers; i++)
        {
            System.out.println("Provide a name:");
            String name = s.next();
            System.out.println("Provide a surname:");
            String surname = s.next();
            System.out.println("Provide the age:");
            int age = s.nextInt();
            User newUser = new User(null, name, surname, age);
            userRepository.save(newUser);
        }

        //querying a driver in the database
        System.out.println("Please provide an ID of a driver to be found: ");
        Long id = s.nextLong();

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            System.out.println(user.get().getFirstName()+" " +user.get().getLastName());
        else
            System.out.println("Driver with ID " + id + " not found");

        // updating driver
        System.out.println("Please provide an ID of a driver to be updated: ");
        Long idUpdate = s.nextLong();

        System.out.println("Provide the marital status:");
        String status = s.next();
        System.out.println("Provide the licence type:");
        String license = s.next();
        System.out.println("Provide the driving skills points:");
        int points = s.nextInt();
        User updatedUser = new User(status, license, points, idUpdate);
        userRepository.update(updatedUser);

        // removing driver
        userRepository.removeById(1L);

        // finding all drivers by age
        int age = 20;
        List<User> usersByAge = userRepository.findAllByAge(age);
        System.out.println("Users aged " + age + ":");
        usersByAge.forEach(u -> System.out.println("Name: "+u.getFirstName()));

        // finding all drivers by marital status
        String maritalStatus = "Single";
        List<User> singleUsers = userRepository.findAllByMaritalStatus(maritalStatus);
        System.out.println("Single Users:");
        singleUsers.forEach(u -> System.out.println("Name: "+u.getFirstName()));

        // Test finding all by licence type
        String licenceType = "A";
        List<User> licenceTypeUsers = userRepository.findAllByLicenceType(licenceType);
        System.out.println("Users with Licence Type " + licenceType + ":");
        licenceTypeUsers.forEach(u -> System.out.println("Name: "+u.getFirstName()));

        // finding all with skill points greater than
        int skill_points = 10;
        List<User> skilledDrivers = userRepository.findAllByPintsGreaterThan(skill_points);
        System.out.println("Drivers with points greater than " + skill_points + ":");
        skilledDrivers.forEach(u -> System.out.println("Name: "+u.getFirstName()));
    }
}
