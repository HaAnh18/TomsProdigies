import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String cID;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String customerType;
    private String userName;
    private String password;

    Path path = Paths.get("./src/customers.txt");
    int id = (int) Files.lines(path).count();

    public Account(String cID, String name, String email, String address, String phone, String customerType, String userName, String password) throws IOException {
        this.cID = cID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.customerType = customerType;
        this.userName = userName;
        this.password = password;
    }

    public Account() throws IOException {
    }

    public void register() throws IOException
    // Register the customer's account
    {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/customers.txt", true));
//        Path path = Paths.get("./src/customers.txt");
//        int id = (int) Files.lines(path).count() - 1;
        cID = String.format("C%03d", id);
        userName = registerUsername();
        name = registerName();
        email = registerEmail();
        address = registerAddress();
        phone = registerPhoneNumber();
        customerType = "Regular";
        password = registerPassword();
        pw.println("\n" + cID + "," + name + "," + email + "," + address + "," + phone + "," + customerType + "," + userName + "," + password);
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
//        String hashing = this.hashingPassword(password);
        try {
            Scanner fileScanner = new Scanner(new File("./src/customers.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (values[6].equals(username))
                // If the username already had in customer file continue to check the password
                {
//                    if (values[7].equals(hashing(password)))
                    if (values[7].equals(password))
//                    // If the password match
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
                if (username.equals(values[6]))
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
            registerName(); // If name is incorrect, customer has to register name again
        }
        return this.name;
    }

    public boolean validateName(String name)
    // Validate the name that customer inputs
    {
        String rulesName = "(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)";
        // Customer's name must contain letters only and have white space
        Pattern pattern = Pattern.compile(rulesName);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches(); // Returns true if name matches, else returns false
    }

    public String registerEmail()
    // Register the customer's email
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        if (validateEmail(email))
        // If the email satisfy the email's rules
        {
            this.email = email;
        } else {
            System.out.println("Invalid email!");
            registerEmail(); // If email is incorrect, customer has to register email again
        }
        return this.email;
    }

    public boolean validateEmail(String email)
    // Validate the email that customer inputs
    {
        String emailRules = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(emailRules);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); // returns true if email matches, else returns false
    }

    public String registerAddress()
    // Register the customer's address
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter address: ");
        String address = scanner.nextLine();
        if (validateAddress(address))
        // If the address satisfy the address's rules
        {
            this.address = address;
        } else {
            System.out.println("Invalid address");
            registerAddress(); // If address is incorrect, customer has to register address again
        }
        return this.address;
    }

    public boolean validateAddress(String address)
    // Validate the address that customer inputs
    {
        String rulesAddress = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        Pattern pattern = Pattern.compile(rulesAddress);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches(); // returns true if address matches, else returns false
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
            registerPhoneNumber(); // If phone number is incorrect, customer has to register phone number again
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
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        if (validatePassword(password))
        // If the password satisfy the password's rules
        {
            this.password = hashing(password);
        } else {
            System.out.println("Your password is too weak!");
            registerPassword(); // If password is not strong enough, customer has to register password again
        }
        return this.password;
    }

    public boolean validatePassword(String password)
    // Validate the password that customer inputs
    {
        String rulesPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        /* Password must contain at least one digit [0-9].
        Password must contain at least one lowercase Latin character [a-z].
        Password must contain at least one uppercase Latin character [A-Z].
        Password must contain a length of at least 8 characters and a maximum of 20 characters. */
        Pattern pattern = Pattern.compile(rulesPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches(); // returns true if password matches, else returns false
    }

    public String hashing(String password)
    // Hashing the customer's password
    {
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void updateName(String filepath, String newData, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[6].equals(userName) && validateName(newData))
            /** If the system could find out the username in customers' file and the new name is validated
             * then the system allow customer to update their information
             */ {
                database.get(i)[1] = newData; // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,membership,username,password", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public void updateEmail(String filepath, String newData, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[6].equals(userName) && validateEmail(newData))
            /** If the system could find out the username in customers' file and the new email is validated
             * then the system allow customer to update their information
             */ {
                database.get(i)[2] = newData; // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,membership,username,password", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public void updateAddress(String filepath, String newData, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[6].equals(userName) && validateAddress(newData))
            /** If the system could find out the username in customers' file and the new address is validated
             * then the system allow customer to update their information
             */ {
                database.get(i)[3] = newData; // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,membership,username,password", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public void updatePhone(String filepath, String newData, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[6].equals(userName) && validatePhoneNumber(newData))
            /** If the system could find out the username in customers' file and the new phone number is validated
             * then the system allow customer to update their information
             */ {
                database.get(i)[4] = newData; // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,membership,username,password", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public void updatePassword(String filepath, String newData, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[6].equals(userName) && validatePassword(newData))
            /** If the system could find out the username in customers' file and the new password is validated
             * then the system allow customer to update their information
             */ {
                database.get(i)[7] = newData; // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,membership,username,password", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public void checkMembership(String userName) throws IOException
    // This method would show the customer's membership status
    {
        String[] database = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/customers.txt", ",");
        // Read all information of this customer
        System.out.println("Membership: " + database[5]); // Print the membership status of this customer
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
