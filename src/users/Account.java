/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tom's Prodigies
  ID: Nguyen Tran Ha Anh - s3938490
      Hoang Tuan Minh - s3924716
      Dang Kim Quang Minh - s3938024
      Nguyen Gia Bao - s3938143
  Acknowledgement:
  https://www.youtube.com/watch?v=qmDUSKqwaL8
  https://stackoverflow.com/questions/60444237/comparing-user-input-with-text-file-reading-text-file
  https://forum.tutorials7.com/1658/match-full-name-java-task-regex
  https://www.geeksforgeeks.org/check-email-address-valid-not-java/
  https://codingnconcepts.com/java/java-regex-to-validate-phone-number/
  https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
  https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

*/

package users;

import fileMethods.CreateTable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Account {
    // Attributes
    private String cID;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String customerType;
    private String username;
    private String password;
    private Long totalSpending;
    private Long totalPoint;

    // Constructor
    public Account(String cID, String name, String email, String address, String phone,
                   String customerType, String userName, String password, Long totalSpending, Long totalPoint) {
        this.cID = cID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.customerType = customerType;
        this.username = userName;
        this.password = password;
        this.totalSpending = totalSpending;
        this.totalPoint = totalPoint;
    }

    // Constructor
    public Account() {
    }


    // Hashing the customer's password
    public String hashing(String password) {
        try {
            // MessageDigest instance for MD5.
            MessageDigest m = MessageDigest.getInstance("MD5");

            // Add plain-text password bytes to digest using MD5 update() method.
            m.update(password.getBytes());

            // Convert the hash value into bytes
            byte[] bytes = m.digest();

            // The bytes array has bytes in decimal form. Converting it into hexadecimal format.
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Complete hashed password in hexadecimal format
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /* Create table to display all types of customer's membership,
     minimum spending in each membership's type
     and discount for each membership's type */
    public void getAllMembershipTypes() {
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("MEMBERSHIP", "MINIMUM SPENDING", "DISCOUNT");
        CreateTable.addRow("Silver", "5 millions VND", "5%");
        CreateTable.addRow("Gold", "10 millions VND", "10%");
        CreateTable.addRow("Platinum", "25 millions VND", "15%");
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // Getter method for customer ID
    public String getcID() {
        return cID;
    }

    // Getter method for customer type
    public String getCustomerType() {
        return customerType;
    }

    // Setter method for customer type
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Getter method for customer's total spending
    public Long getTotalSpending() {
        return totalSpending;
    }

    // Setter method for customer's total spending
    public Long setTotalSpending(Long totalSpending) {
        this.totalSpending = totalSpending;
        return this.totalSpending;
    }

    // Setter method for customer ID
    public void setcID(String cID) {
        this.cID = cID;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for address
    public String getAddress() {
        return address;
    }

    // Setter method for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter method for phone number
    public String getPhone() {
        return phone;
    }

    // Setter method for phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter method for total point
    public Long getTotalPoint() {
        return totalPoint;
    }

    // Setter method for total point
    public void setTotalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
    }
}
