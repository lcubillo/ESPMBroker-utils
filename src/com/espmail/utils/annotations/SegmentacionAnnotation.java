package com.espmail.utils.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aspmail
 * User: Luis Cubillo
 * Date: 15-jul-2009
 * Time: 13:52:43
 */

   @Retention(RetentionPolicy.RUNTIME)
   @Target(java.lang.annotation.ElementType.METHOD)

public @interface SegmentacionAnnotation {

   String tipo() default "pais";      

}
