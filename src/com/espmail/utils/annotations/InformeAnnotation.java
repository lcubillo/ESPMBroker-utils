package com.espmail.utils.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aspmail
 * User: Luis Cubillo
 * Date: 15-jun-2009
 * Time: 17:39:18
 */

   @Retention (RetentionPolicy.RUNTIME)
   @Target (java.lang.annotation.ElementType.METHOD)

public @interface InformeAnnotation {
   String tipo() default "String";
   String titulo() default "----";
   int orden() default -1;
   String informe() default "";
}

