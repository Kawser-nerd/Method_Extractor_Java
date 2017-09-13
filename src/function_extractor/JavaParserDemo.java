package function_extractor;

import java.io.File;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

public class JavaParserDemo {

  public static void main(String[] args) {
    String data_folder = "./data/";
    // CuPrinter(args[0]);

  }

  public static void CuPrinter(String filePath) {
    try {
      CompilationUnit cu = JavaParser.parse(new File(filePath));
      if (cu != null) {
        MethodVisitor mv = new MethodVisitor();
        cu.accept(mv, null);
      }

    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // return "1";
  }
}


