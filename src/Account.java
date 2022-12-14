import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String cID;
    private String name;
    private String address;
    private String phone;
    private String customerType;
    private String userName;
    private String password;


    public Account(String cID, String name, String address, String phone, String customerType, String userName, String password) {
        this.cID = cID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.customerType = customerType;
        this.userName = userName;
        this.password = password;
    }

    public Account() {
    }

    public void register() throws IOException
    // Register the customer's account
    {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/customers.txt", true));
        Path path = Paths.get("./src/customers.txt");
        int id = (int) Files.lines(path).count() - 1;
        cID = String.format("C%03d", id);
        userName = registerUsername();
        name = registerName();
        System.out.println("Enter address: ");
        address = scanner.nextLine();
        phone = registerPhoneNumber();
        customerType = "Regular";
        password = registerPassword();
        pw.println("\n" + cID + "," + name + "," + address + "," + phone + "," + customerType + "," + userName + "," + password);
        // Write customer's information to customers file
        pw.close();
    }

    public boolean login()
    // Login if he/she had account already
    {
        boolean isAuthentication = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File("./src/customers.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (values[5].equals(username))
                // If the username already had in customer file continue to check the password
                {
                    if (values[6].equals(password))
                    // If the password match
                    {
                        isAuthentication = true;
                    }
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return isAuthentication;
    }

    public String registerUsername()
    // Register the username
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File("./src/customers.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");
                if (values[5].equals(username))
                // If the username had been existed, the customer had to create another username
                {
                    System.out.println("Username had been existed");
                    registerUsername();
                } else {
                    userName = username;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return userName;
    }

    public String registerName()
    // Register the customer's name
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        if (validateName(name))
        // If the name is satisfied the name's rules
        {
            this.name = name;
        } else {
            System.out.println("Invalid name");
            registerName();
        }
        return this.name;
    }

    public boolean validateName(String name)
    // Validate the name that customer input
    {
        String rulesName = "(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)";
        // Customer's name must contain letters only and have white space
        Pattern pattern = Pattern.compile(rulesName);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches(); // Returns true if name matches, else returns false
    }

    public String registerPhoneNumber()
    // Register the phone number
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter phone number: ");
        String phone = scanner.nextLine();
        if (validatePhoneNumber(phone))
        // If the phone number satisfy the phone number's rules
        {
            this.phone = phone;
        } else {
            System.out.println("Invalid phone number!");
            registerPhoneNumber();
        }
        return this.phone;
    }

    public boolean validatePhoneNumber(String phoneNumber)
    // Validate the phone number that customer input
    {
        String rulesPhoneNumber = "^\\d{10}$"; // Phone number only contains 10 digits from 0 to 9
        Pattern pattern = Pattern.compile(rulesPhoneNumber);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches(); // returns true if phone number matches, else returns false
    }

    public String registerPassword()
    // Register the password
    {
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        if (validatePassword(password))
        // If the password satisfy the password's rules
        {
            this.password = password;
        } else {
            System.out.println("Your password is too weak!");
            registerPassword();
        }
        return this.password;
    }

    public boolean validatePassword(String password) {
        String rulesPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        /* Password must contain at least one digit [0-9].
        Password must contain at least one lowercase Latin character [a-z].
        Password must contain at least one uppercase Latin character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters. */
        Pattern pattern = Pattern.compile(rulesPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); // returns true if password matches, else returns false
    }
}
