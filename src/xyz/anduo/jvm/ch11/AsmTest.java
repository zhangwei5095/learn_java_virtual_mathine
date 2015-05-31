/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.anduo.jvm.ch11;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

/**
 *
 * @author anduo
 */
public class AsmTest extends ClassLoader{

    public static void main(String[] args) throws Exception {
        //int a = 6;
        //int b = 7;
        //int c = (a+b)*3;
        //System.out.println(c);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mw.visitVarInsn(ALOAD,0);//this 入栈
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(0, 0);
        mw.visitEnd();
        
        mw = cw.visitMethod(ACC_PUBLIC+ACC_STATIC, "main", "([Ljava/lang/String;)V", null,null);
        mw.visitFieldInsn(GETSTATIC, "java/lang/System","out" , "Ljava/io/PrintStream;");
        mw.visitLdcInsn("hello world!");
        mw.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream" , "println", "(Ljava/lang/String;)V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(0, 0);
        mw.visitEnd();
        
        byte[] code = cw.toByteArray();
        AsmTest loader = new AsmTest();
        Class exampleClass = loader.defineClass("Example",code,0,code.length);
        exampleClass.getMethods()[0].invoke(null, new Object[]{null});
        
    }
}
