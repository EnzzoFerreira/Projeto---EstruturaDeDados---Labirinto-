package classesAuxiliares;

import java.lang.reflect.*;


public class Clonador<X> {
    public X clone (X x) {
        Class<?> classe = x.getClass();

        Method metodo = null;
        try {
            metodo = classe.getMethod("clone");
        } catch (NoSuchMethodException erro) {
            throw new RuntimeException("Método clone não encontrado!");
        }
        
        Object[] parmsReais = null;

        X ret = null;

        try {
            ret = (X) metodo.invoke(x, parmsReais);
        } catch(InvocationTargetException erro) {}
        catch (IllegalAccessException erro) {}

        return ret;
    }
}