import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

public class menuGUITesting implements ActionListener {
    private static JLabel label = new JLabel();
    private static JTextField userText = new JTextField();
    private static JLabel passwordLabel = new JLabel();
    private static JButton loginButton = new JButton();
    private static JButton cancelButton = new JButton();
    private static JTextField passwordText = new JPasswordField();
    private static JLabel failMessage = new JLabel();
    private static JPanel panel = new JPanel();
    private static JFrame frame = new JFrame();
    public static void adminLoginMenu() {
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        label = new JLabel("Username");
        label.setBounds(10, 20, 80, 25);
        panel.add(label);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new menuGUITesting());
        panel.add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 80, 80 ,25);
        panel.add(cancelButton);

        failMessage = new JLabel("Login failed, try again!!!");
        failMessage.setBounds(100, 100, 165, 25);


        frame.setVisible(true);

    }

    public static void main(String[] args) {
        adminLoginMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();

        Admin admin = null;
        try {
            admin = new Admin();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        boolean cookie = false;
        if(!admin.verifyAdmin(user,password)) {
            JOptionPane.showMessageDialog(frame,"Login failed, please check your login info!");
        } else {
            JOptionPane.showMessageDialog(frame, "Login Succesfully!");

            AdminMenu menuA = new AdminMenu();
            try {
                menuA.viewHomepage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        cookie = true;
    }
}
