package projectUpload;

import java.awt.EventQueue;
import org.apache.commons.io.*;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.util.Collection;
import java.util.Iterator;
import java.awt.Color;
import javax.swing.UIManager;
import function_extractor.*;


public class ProjectUpload {

  private JFrame frame;
  private JTextField textField;
  final String Project_Folder = System.clearProperty("user.dir") + "/data/";

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ProjectUpload window = new ProjectUpload();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public ProjectUpload() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    textField = new JTextField();
    textField.setBounds(49, 62, 223, 35);
    frame.getContentPane().add(textField);
    textField.setColumns(10);

    ///// Project Folder Selection /////

    JButton btnSelectProject = new JButton("Select Project");
    btnSelectProject.setBounds(284, 67, 132, 25);
    final JFileChooser fc = new JFileChooser();
    btnSelectProject.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fc.showOpenDialog(btnSelectProject);
        File path = fc.getCurrentDirectory().getAbsoluteFile();
        textField.setText(path.toString());
      }
    });
    frame.getContentPane().add(btnSelectProject);

    JButton btnNewButton = new JButton("Upload");
    btnNewButton.setBounds(155, 109, 117, 25);
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        File source = new File(textField.getText());
        String project_name = source.getName();
        File destination = new File(Project_Folder + project_name);
        try {
          FileUtils.copyDirectory(source, destination);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    frame.getContentPane().add(btnNewButton);

    JLabel lblCropsimProjectUploader = new JLabel("CroPSim Project Uploader ");
    lblCropsimProjectUploader.setBackground(
        UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
    lblCropsimProjectUploader.setForeground(Color.BLACK);
    lblCropsimProjectUploader.setBounds(135, 12, 213, 25);
    frame.getContentPane().add(lblCropsimProjectUploader);

    //// Folder Selection For Extraction ////

    JComboBox comboBox = new JComboBox();
    File folder = new File(Project_Folder);
    File[] listOfFiles = folder.listFiles();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isDirectory() && !listOfFiles[i].isHidden()) {
        comboBox.addItem(listOfFiles[i].getName());
      }
    }

    /*
     * comboBox.addActionListener(new ActionListener() { public void
     * actionPerformed(ActionEvent arg0) {
     * 
     * System.out.println(listOfFiles[0].toString()); for (int i = 0; i <
     * listOfFiles.length; i++) { System.out.println(listOfFiles[i].toString());
     * comboBox.addItem(listOfFiles[i].toString()); } } });
     */
    comboBox.setBounds(91, 163, 160, 24);
    frame.getContentPane().add(comboBox);

    JButton btnMergeFiles = new JButton("Merge Files");
    btnMergeFiles.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String project_name = comboBox.getSelectedItem().toString();
        String full_project = Project_Folder + project_name;
        File Java_Files = new File(full_project);
        String[] Extension = { "java" };
        Collection files = FileUtils.listFiles(Java_Files, Extension, true);
        String Temp_path = full_project + File.separator + "merged.txt";
        PrintWriter pw;
        try {
          pw = new PrintWriter(Temp_path);
          for (Iterator iterator = files.iterator(); iterator.hasNext();) {
            File read_file = (File) iterator.next();
            // JavaParserDemo.CuPrinter(read_file.toString());
            BufferedReader br;
            try {
              br = new BufferedReader(new FileReader(read_file));
              String line;
              try {
                line = br.readLine();
                while (line != null) {
                  pw.println(line);
                  line = br.readLine();
                }
                br.close();
              } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }

            } catch (FileNotFoundException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }

          }
          pw.flush();
          pw.close();
        } catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    btnMergeFiles.setBounds(284, 163, 117, 25);
    frame.getContentPane().add(btnMergeFiles);

    JButton btnExtractor = new JButton("Extractor");
    btnExtractor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String project_name = comboBox.getSelectedItem().toString();
        String dest = Project_Folder + project_name + File.separator
            + "extracted_methods.txt";
        File file = new File(dest);
        try {
          file.createNewFile();
          MethodVisitor.selectedProject(dest);
          String full_project = Project_Folder + project_name;
          File Java_Files = new File(full_project);
          String[] Extension = { "java" };
          Collection files = FileUtils.listFiles(Java_Files, Extension, true);
          for (Iterator iterator = files.iterator(); iterator.hasNext();) {
            File read_file = (File) iterator.next();
            JavaParserDemo.CuPrinter(read_file.toString());
          }
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }

      }
    });
    btnExtractor.setBounds(155, 199, 117, 25);
    frame.getContentPane().add(btnExtractor);

  }
}
