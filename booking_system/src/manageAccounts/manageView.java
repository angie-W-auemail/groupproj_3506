package manageAccounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class manageView extends JFrame {

    public manageView() {
    	 JFrame frame = new JFrame("Frame");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(500,500);
         frame.setLayout(new BorderLayout());

         final JTextPane textPane;
         textPane = new JTextPane();
         frame.add(textPane,BorderLayout.CENTER);

         JButton button = new JButton("Load Patients");
         button.addActionListener(new ActionListener()  {
             public void actionPerformed(ActionEvent e) {
                         	 
                 textPane.setText("");
                 String fileResult = "";                 
                 try {
                 BufferedReader csvReader = new BufferedReader(new FileReader("src/patients.csv"));
                 String line = null;
                 while ((line = csvReader.readLine()) != null) {
                     
                     fileResult = fileResult + "\n\n" +line;
                 }
                 }
                 catch(FileNotFoundException ex) {
                     System.err.println("File was not found");
                 }
                 catch(IOException ioe) {
                     System.err.println("There was an error while reading the file");
                 }
                 textPane.setText(fileResult);
             }
         });
         frame.add(button,BorderLayout.SOUTH);

         frame.setVisible(true);
     }

 }