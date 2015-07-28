package xyz.anduo.jvm.ch11;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class HomeWork extends ClassLoader{
  
  public static void main(String[] args) throws Exception {
    
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
    cw.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
    
    MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
    mv.visitCode();
    
    Label lable0 = new Label();
    mv.visitLabel(lable0);
    mv.visitLineNumber(5, lable0);
    mv.visitIntInsn(BIPUSH, 6);
    mv.visitVarInsn(ISTORE, 1);
    
    Label lable1 = new Label();
    mv.visitLabel(lable1);
    mv.visitLineNumber(6, lable1);
    mv.visitIntInsn(BIPUSH, 7);
    mv.visitVarInsn(ISTORE, 2);
    
    //add 
    Label lable2 = new Label();
    mv.visitLabel(lable2);
    mv.visitLineNumber(7, lable2);
    mv.visitVarInsn(ILOAD, 1);
    mv.visitVarInsn(ILOAD, 2);
    mv.visitInsn(IADD);
    //赋值给c
    mv.visitInsn(ICONST_3);
    mv.visitInsn(IMUL);
    mv.visitVarInsn(ISTORE, 3);
    
    Label lable3 = new Label();
    mv.visitLabel(lable3);
    mv.visitLineNumber(8, lable3);
    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    mv.visitVarInsn(ILOAD, 3);
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V");
    
    Label lable4 = new Label();
    mv.visitLabel(lable4);
    mv.visitLineNumber(9, lable4);
    mv.visitInsn(RETURN);
    
    Label lable5 = new Label();
    mv.visitLabel(lable5);
    mv.visitLocalVariable("args", "[Ljava/lang/String;", null, lable0, lable5, 0);
    mv.visitLocalVariable("a", "I", null, lable1, lable5, 1);
    mv.visitLocalVariable("b", "I", null, lable2, lable5, 2);
    mv.visitLocalVariable("c", "I", null, lable3, lable5, 3);
    mv.visitMaxs(2, 4);
    mv.visitEnd();
    
    
    byte[] code = cw.toByteArray();
    HomeWork loader = new HomeWork();
    Class<?> exampleClass = loader.defineClass("Example",code,0,code.length);
    exampleClass.getMethods()[0].invoke(null, new Object[]{null});
    
  }
}
