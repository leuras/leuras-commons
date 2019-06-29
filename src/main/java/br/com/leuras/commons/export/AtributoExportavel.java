package br.com.leuras.commons.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface AtributoExportavel {
    
    public String coluna() default StringUtils.EMPTY;
    
    public boolean ignorar() default false;
    
    public FormatoAtributoEnum formato() default FormatoAtributoEnum.NENHUM;
}
