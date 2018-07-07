package com.flair.client.localization.annotations;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedFieldType;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 * Marks localized widgets for deferred binding
 */
@Retention(RUNTIME)
@Target(FIELD)
@Repeatable(LocalizedCommonFieldContainer.class)
public @interface LocalizedCommonField {
	CommonLocalizationTags tag() default CommonLocalizationTags.INVALID;
	LocalizedFieldType type() default LocalizedFieldType.TEXT_BASIC;
}
