package com.flair.client.localization.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.flair.client.localization.LocalizedFieldType;

/*
 * Marks localized widgets for deferred binding
 */
@Retention(RUNTIME)
@Target(FIELD)
@Repeatable(LocalizedFieldContainer.class)
public @interface LocalizedField
{
	String 					provider() 	default "";			// by default, resolved automatically during binding
	String 					tag() 		default "";			// same as above
	LocalizedFieldType 		type() 		default LocalizedFieldType.TEXT_BASIC;
}
