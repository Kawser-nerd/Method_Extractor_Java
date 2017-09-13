package function_extractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.tools.javac.util.List;


public class MethodVisitor extends VoidVisitorAdapter<Void> {

  static String Project_Selected = null;
  static ArrayList<String> methodName = new ArrayList<String>();
  static int count = 0;
  BufferedWriter bw = null;

  public static void selectedProject(String file_read) {
    Project_Selected = file_read;
  }

  @Override
  public void visit(MethodDeclaration n, Void arg) {
    /*
     * here you can access the attributes of the method. MethodVisitor method
     * will be called for all methods in MethodVisitor CompilationUnit,
     * including inner class methods
     */
    try {
      bw = new BufferedWriter(new FileWriter(Project_Selected, true));
      bw.write(n.getName());
      bw.newLine();
      bw.flush();
      bw.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } // end tr
  }
}

/******
 * public void fileWriteOnProject() { String[] array = (String[])
 * methodName.toArray(); for (int i = 0; i < array.length; i++) {
 * System.out.println(array[i]); } FileWriter fstream; try { fstream = new
 * FileWriter(Project_Selected, true); BufferedWriter out = new
 * BufferedWriter(fstream);
 * 
 * for (int i = 0; i < array.length; i++) { out.write(array[i]); out.newLine();
 * }
 * 
 * out.close(); fstream.close();
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } }
 */