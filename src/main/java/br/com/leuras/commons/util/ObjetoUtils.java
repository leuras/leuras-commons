package br.com.leuras.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public final class ObjetoUtils {
    
    private ObjetoUtils() {
        
    }
    
    /**
     * Invoca um método <b>set</b> de um atributo por reflexão.
     * 
     * @param instance
     *            Objeto
     * @param field
     *            Atributo que se deseja fazer a atribuição
     * @param value
     *            Valor a ser atribuído
     * @throws NoSuchMethodException
     *             Caso o método <b>set</b> não tenha sido encontrado na classe.
     * @throws IllegalAccessException
     *             Caso o método <b>set</b> não possa ser invocado por restrições de acesso (modificadores).
     * @throws IllegalArgumentException
     *             Caso um ou mais parâmetros inválidos tenham sido passados ao método <b>set</b>.
     * @throws InvocationTargetException
     *             Caso algum outro erro inesperado seja disparado ao invocar o método <b>set</b>.
     */
    public static void invokeSet(final Object instance, final Field field, final Object value)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        final Class<?> objClass = instance.getClass();
        final String method = String.format("set%s", StringUtils.capitalize(field.getName()));

        final Method objMethod = objClass.getMethod(method, field.getType());
        objMethod.setAccessible(true);

        objMethod.invoke(instance, value);
    }
    
    /**
     * Invoca um método <b>get</b> de um atributo por reflexão.
     * 
     * @param instance
     *            Objeto
     * @param field
     *            Atributo que se deseja obter
     * @return O valor obtido ao invocar o método <b>get</b> do objeto passado como parâmetro.
     * @throws NoSuchMethodException
     *             Caso o método <b>get</b> não tenha sido encontrado na classe.
     * @throws IllegalAccessException
     *             Caso o método <b>get</b> não possa ser invocado por restrições de acesso (modificadores).
     * @throws IllegalArgumentException
     *             Caso um ou mais parâmetros inválidos tenham sido passados ao método <b>get</b>.
     * @throws InvocationTargetException
     *             Caso algum outro erro inesperado seja disparado ao invocar o método <b>get</b>.
     */
    public static Object invokeGet(final Object instance, final Field field)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Class<?> objClass = instance.getClass();
        String method = String.format("get%s", StringUtils.capitalize(field.getName()));

        Method objMethod = objClass.getMethod(method);
        objMethod.setAccessible(true);

        return objMethod.invoke(instance);
    }
    
    
}
