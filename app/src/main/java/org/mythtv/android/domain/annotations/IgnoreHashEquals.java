package org.mythtv.android.domain.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/26/17.
 */
@Retention( SOURCE )
@Target( { METHOD, PARAMETER, FIELD } )
public @interface IgnoreHashEquals {
}
