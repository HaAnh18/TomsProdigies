package users;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.Write;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer extends Account {
    public Customer(String cID, String name, String email, String address,
                    String phone, String customerType, String userName,
                    String password, Long totalSpending, Long totalPoints) throws IOException {
        super(cID, name, email, address, phone, customerType, userName, password, totalSpending, totalPoints);
    }

    public Customer() throws IOException {
    }

    public void register() throws IOException
    // Register the customer's account
    {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/customers.txt", true));
        Random rd = new Random();
        int id = rd.nextInt(999);
        setcID(validateCId(String.format("C%03d", id)));
        setUsername(registerUsername());
        setName(registerName());
        setEmail(registerEmail());
        setAddress(registerAddress());
        setPhone(registerPhoneNumber());
        setCustomerType("Regular"); // When customer register his/her account, their membership will be set to "Regular"
        setPassword(registerPassword());
        setTotalSpending((long) 0); // When customer register his/her account, their total spending will be set to 0
        setTotalPoint((long) 0);
        pw.println(getcID() + "," + getName() + "," + getEmail() + "," + getAddress() + "," + getPhone() + "," + getCustomerType() + ","
                + getUsername() + "," + getPassword() + "," + getTotalPoint() + "," + getTotalPoint());
        // FileMethods.Write customer's information to customers file
        pw.close();
    }

    public String validateCId(String cID)
    // Validate customer ID to make sure that CID of each customer will be unique
    {
        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/customers.txt"));

            while (fileScanner.hasNext())
            // The system will read the file and while it has next line, it will do following steps
            {
                String line = fileScanner.nextLine();
                String[] customerInfo = line.split(","); // Split each value in each line by comma
                if (customerInfo[0].equals(cID))
                /* If this customer ID had already existed in customers' file,
                new customer ID will be generated randomly and validate again
                 */ {
                    Random random = new Random();
                    cID = String.format("0%03d", random.nextInt(999));
                    validateCId(cID);
                } else {
                    setcID(cID);
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return getcID();
    }

    public boolean login(String username, String password)
    // Login if he/she had account already
    {
        boolean isAuthentication = false;

        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/customers.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (values[6].equals(username))
                // If the username already had in customer file continue to check the password
                {
                    if (values[7].equals(hashing(password)))
//                    if (values[7].equals(password))
//                    // If the password match, it will return true
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
        System.out.print("Enter username (e.g: mingka): ");
        String username = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/customers.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (username.equals(values[6]))
                // If the username had been existed, the customer had to create another username
                {
                    System.out.println("Username had been existed");
                    registerUsername();
                } else {
                    setUsername(username);
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return getUsername();
    }

    public String registerName()
    // Register the customer's name
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name (Ha Anh): ");
        String name = scanner.nextLine();
        if (validateName(name))
        // If the name is satisfied the name's rules, the name will be saved in customer's information
        {
            setName(name);
        } else {
            System.out.println("Invalid name");
            registerName(); // If name is incorrect, customer has to register name again
        }
        return getName();
    }

    public boolean validateName(String name)
    // Validate the name that customer inputs
    {
        String rulesName = "(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)";
        // Users.Customer's name must contain letters only and have white space
        Pattern pattern = Pattern.compile(rulesName);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches(); // Returns true if name matches, else returns false
    }

    public String registerEmail()
    // Register the customer's email
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email (e.g: tomsprodigies@gmail.com): ");
        String email = scanner.nextLine();
        if (validateEmail(email))
        // If the email satisfy the email's rules, the email will be saved in customer's information
        {
            setEmail(email);
        } else
        // If email is incorrect, customer has to register email again
        {
            System.out.println("Invalid email!");
            registerEmail();
        }
        return getEmail();
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
        System.out.print("Enter address (e.g: 702 Nguyen Van Linh Street): ");
        String address = scanner.nextLine();
        if (validateAddress(address))
        // If the address satisfy the address's rules, the address will be saved in customer's information
        {
            setAddress(address);
        } else
        // If address is incorrect, customer has to register address again
        {
            System.out.println("Invalid address");
            registerAddress();
        }
        return getAddress();
    }

    public boolean validateNumber(String num) {
        String rulesNumber = "-?\\d+(\\.\\d+)?";
        Pattern pattern = Pattern.compile(rulesNumber);
        Matcher matcher = pattern.matcher(num);
        return matcher.matches(); // returns true if address matches, else returns false
    }

    public boolean validateAddressSingle(String address)
    // Validate the address that customer inputs
    {
//        String rulesAddress = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        String rulesAddress = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        Pattern pattern = Pattern.compile(rulesAddress);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches(); // returns true if address matches, else returns false
    }

    public boolean validateAddress(String address) {
        String[] singleAddress = address.split(" ");
        boolean check = false;
        for (int i = 0; i < singleAddress.length - 1; i++) {
            if (validateNumber(singleAddress[0])) {
                if (validateAddressSingle(singleAddress[i + 1])) {
                    check = true;
                }
            }
        }
        return check;
    }

    public String registerPhoneNumber()
    // Register the phone number
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter phone number (e.g: 09********): ");
        String phone = scanner.nextLine();
        if (validatePhoneNumber(phone))
        // If the phone number satisfy the phone number's rules, the phone number will be saved in customer's information
        {
            setPhone(phone);
        } else
        // If phone number is incorrect, customer has to register phone number again
        {
            System.out.println("Invalid phone number!");
            registerPhoneNumber();
        }
        return getPhone();
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
        System.out.print("Enter password \n" +
                "        (Password must contain at least one digit [0-9].\n" +
                "        Password must contain at least one lowercase Latin character [a-z].\n" +
                "        Password must contain at least one uppercase Latin character [A-Z].\n" +
                "        Password must contain a length of at least 8 characters and a maximum of 20 characters): ");
        String password = scanner.nextLine();
        if (validatePassword(password))
        // If the password satisfy the password's rules, the password will be saved in customer's information
        {
            setPassword(hashing(password));
        } else
        // If password is not strong enough, customer has to register password again
        {
            System.out.println("Your password is invalid!");
            registerPassword();
        }
        return getPassword();
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

    public void updateInfo(String filepath, String newData, String option) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
        for (String[] strings : database) {
            switch (option) {
                case "1":
                    if (strings[6].equals(this.getUsername()) && validateName(newData))
                        /* If the system could find out the username in customers' file and the new name is validated
                         * then the system allow customer to update their information
                         */ {
                        this.setName(newData);
                        strings[1] = this.getName(); // The customer's information is changed in arraylist
                    }
                    break;
                case "2":
                    if (strings[6].equals(this.getUsername()) && validateEmail(newData))
                        /* If the system could find out the username in customers' file and the new email is validated
                         * then the system allow customer to update their information
                         */ {
                        this.setEmail(newData);
                        strings[2] = this.getEmail(); // The customer's information is changed in arraylist
                    }
                    break;
                case "3":
                    if (strings[6].equals(this.getUsername()) && validateAddress(newData))
                        /* If the system could find out the username in customers' file and the new address is validated
                         * then the system allow customer to update their information
                         */ {
                        this.setAddress(newData);
                        strings[3] = this.getAddress(); // The customer's information is changed in arraylist
                    }
                    break;
                case "4":
                    if (strings[6].equals(getUsername()) && validatePhoneNumber(newData))
                        /* If the system could find out the username in customers' file and the new phone number is validated
                         * then the system allow customer to update their information
                         */ {
                        this.setPhone(newData);
                        strings[4] = this.getPhone(); // The customer's information is changed
                    }
                    break;
                case "5":
                    if (strings[6].equals(this.getUsername()) && validatePassword(newData))
                        /* If the system could find out the username in customers' file and the new password is validated
                         * then the system allow customer to update their information
                         */ {
                        this.setPassword(this.hashing(newData));
                        strings[7] = this.getPassword(); // The customer's information is changed
                    }
                    break;
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : database) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }


//    public void updateName(String filepath, String newData) throws IOException
//    // Update a new name in customer's information
//    {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
//        // Read all line in customers.txt file and put all data in arraylist
//        for (String[] strings : database) {
//            if (strings[6].equals(this.getUsername()) && validateName(newData))
//                /* If the system could find out the username in customers' file and the new name is validated
//                 * then the system allow customer to update their information
//                 */
//            {
//                this.setName(newData);
//                strings[1] = this.getName(); // The customer's information is changed in arraylist
//            }
//        }
//        File file = new File(filepath);
//        PrintWriter pw = new PrintWriter(file);
//
//        pw.write(""); // The file would erase all the data in customers' file
//        pw.close();
//
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
//                    String.join(",", obj));
//            // This method would allow system to write all data including new data into the customers' file
//        }
//    }

//    public void updateEmail(String filepath, String newData) throws IOException
//    // Update a new email in customer's information
//    {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
//        // Read all line in customers.txt file and put all data in arraylist
//        for (String[] strings : database) {
//            if (strings[6].equals(this.getUsername()) && validateEmail(newData))
//                /* If the system could find out the username in customers' file and the new email is validated
//                 * then the system allow customer to update their information
//                 */
//            {
//                this.setEmail(newData);
//                strings[2] = this.getEmail(); // The customer's information is changed in arraylist
//            }
//        }
//        File file = new File(filepath);
//        PrintWriter pw = new PrintWriter(file);
//
//        pw.write(""); // The file would erase all the data in customers' file
//        pw.close();
//
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
//                    String.join(",", obj));
//            // This method would allow system to write all data including new data into the customers' file
//        }
//    }

//    public void updateAddress(String filepath, String newData) throws IOException
//    // Update a new address in customer's information
//    {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
////        ArrayList<String[]> database = FileMethods.ReadDataFromTXTFile.readAllLines("./src/customers.txt");
//        // Read all line in customers.txt file and put all data in arraylist
//        for (String[] strings : database) {
//            if (strings[6].equals(this.getUsername()) && validateAddress(newData))
//                /* If the system could find out the username in customers' file and the new address is validated
//                 * then the system allow customer to update their information
//                 */
//            {
//                this.setAddress(newData);
//                strings[3] = this.getAddress(); // The customer's information is changed in arraylist
//            }
//        }
//        File file = new File(filepath);
//        PrintWriter pw = new PrintWriter(file);
//
//        pw.write(""); // The file would erase all the data in customers' file
//        pw.close();
//
//
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
//                    String.join(",", obj));
//            // This method would allow system to write all data including new data into the customers' file
//        }
//    }

//    public void updatePhone(String filepath, String newData) throws IOException
//    // Update a new phone number in customer's information
//    {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
//        // Read all line in customers.txt file and put all data in arraylist
//        for (String[] strings : database) {
//            if (strings[6].equals(getUsername()) && validatePhoneNumber(newData))
//                /* If the system could find out the username in customers' file and the new phone number is validated
//                 * then the system allow customer to update their information
//                 */
//            {
//                this.setPhone(newData);
//                strings[4] = this.getPhone(); // The customer's information is changed
//            }
//        }
//        File file = new File(filepath);
//        PrintWriter pw = new PrintWriter(file);
//
//        pw.write(""); // The file would erase all the data in customers' file
//        pw.close();
//
////        ArrayList<String[]> newDatabase = database;
//
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
//                    String.join(",", obj));
//            // This method would allow system to write all data including new data into the customers' file
//        }
//    }
//
//    public void updatePassword(String filepath, String newData) throws IOException
//    // Update a new password in customer's information
//    {
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
//        // Read all line in customers.txt file and put all data in arraylist
//        for (String[] strings : database) {
//            if (strings[6].equals(this.getUsername()) && validatePassword(newData))
//                /* If the system could find out the username in customers' file and the new password is validated
//                 * then the system allow customer to update their information
//                 */
//            {
//                this.setPassword(this.hashing(newData));
//                strings[7] = this.getPassword(); // The customer's information is changed
//            }
//        }
//        File file = new File(filepath);
//        PrintWriter pw = new PrintWriter(file);
//
//        pw.write(""); // The file would erase all the data in customers' file
//        pw.close();
//
//        for (String[] obj : database) {
//            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
//                    String.join(",", obj));
//            // This method would allow system to write all data including new data into the customers' file
//        }
//    }


    public void checkMembership() throws IOException
    // Display the customer's membership status
    {
//        String[] database = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/dataFile/customers.txt", ",");
//        // Read all information of this customer
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("MEMBERSHIP");
        CreateTable.addRow(this.getCustomerType()); // Print the membership status of this customer
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }


    public void updateTotalSpending(String filepath, String newData) throws IOException
    // Update total spending of customer after he/she finished every order
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customers.txt");
        // Read all line in customers.txt file and put all data in arraylist
        for (String[] strings : database) {
            if (strings[6].equals(this.getUsername()))
                /* If the system could find out the username in customers' file and the new password is validated
                 * then the system allow customer to update their information
                 */ {
                this.setTotalSpending(Long.parseLong(newData));
                strings[8] = String.valueOf(this.getTotalSpending()); // The customer's information is changed
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();


        for (String[] obj : database) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }


    public void updateMembership(String filepath) throws IOException
    // Update customer's membership when he/she reached the minimum spending in every membership's requirement
    {

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines(filepath);
        // Read all line in customers.txt file and put all data in arraylist

        // Loop through all customers
        for (String[] strings : database) {
            // Find corresponding user
            if (strings[6].equals(this.getUsername())) {
                long compareNum = Long.parseLong(strings[8]);

                // Membership requirements and newMembership variable use to update the membership
                if (5000000 < compareNum && compareNum < 10000000) {
                    this.setCustomerType("Silver");
                    strings[5] = this.getCustomerType();
                } else if (10000000 < compareNum && compareNum < 25000000) {
                    this.setCustomerType("Gold");
                    strings[5] = this.getCustomerType();
                } else if (25000000 < compareNum) {
                    this.setCustomerType("Platinum");
                    strings[5] = this.getCustomerType();
                }
            }
        }
        // setting up the PrintWriter
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();


        // Rewrite the whole file with new updated information
        for (String[] obj : database) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }
}
