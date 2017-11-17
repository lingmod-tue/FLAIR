package com.flair.client.localization.generators;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flair.client.localization.DefaultLocalizationProviders;
import com.flair.client.localization.LocalizationBinderData;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedCommonFieldContainer;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.annotations.LocalizedFieldContainer;
import com.flair.client.localization.wrappers.SimpleLocalizedAltTextWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedListBoxOptionWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedNavBrandWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedTextBoxWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedTextWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedTitleWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedTooltipWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.uibinder.rebind.IndentedWriter;

/*
 * Generates localization data for annotated widgets
 * Using code from http://blog.jdevelop.eu/?p=515
 */
public class LocalizationBinderGenerator extends BaseGenerator
{

	@Override
	protected void doGenerate(JClassType interfaceType, String implName, IndentedWriter writer, TreeLogger logger, TypeOracle oracle) throws UnableToCompleteException
	{
		JParameter[] methodParams = extractInterfaceMethodParams(interfaceType);
		writeImports(writer, methodParams, oracle);
		writeClassIntro(interfaceType, implName, writer);
		writeFieldsIntro(writer);
		writeMethodIntro(writer, methodParams);
		writeFieldsBinding(interfaceType, writer, logger);
		writeMethodOutro(writer);
		writeClassOutro(writer);
	}
	
	private static class WrapperData
	{
		public String 				provider;
		public String 				tag;
		public LocalizedFieldType 	type;
	}
	
	private void generateLocalizedFieldWrapperData(LocalizedField lf,
			String parentClassName, String fieldName, List<WrapperData> out, Set<LocalizedFieldType> usedTypes, TreeLogger logger) throws UnableToCompleteException
	{
		WrapperData newWrapper = new WrapperData();
		newWrapper.provider = lf.provider();
		newWrapper.tag = lf.tag();
		newWrapper.type = lf.type();
		
		// use the parent class's name as the provider and the field name as the tag by default
		if (newWrapper.provider.isEmpty())
			newWrapper.provider = parentClassName;
		
		if (newWrapper.tag.isEmpty())
			newWrapper.tag = fieldName;
		
		// can't have more than one marker for a given type
		if (usedTypes.contains(newWrapper.type))
		{
			logger.log(Type.ERROR, "Multiple localization markers for type " + newWrapper.type);
			throw new UnableToCompleteException();
		}
		
		out.add(newWrapper);
		usedTypes.add(newWrapper.type);
	}
	
	private void generateLocalizedCommonFieldWrapperData(LocalizedCommonField lcf,
			List<WrapperData> out, Set<LocalizedFieldType> usedTypes, TreeLogger logger) throws UnableToCompleteException
	{
		WrapperData newWrapper = new WrapperData();
		newWrapper.provider = DefaultLocalizationProviders.COMMON.toString();
		newWrapper.tag = lcf.tag().toString();
		newWrapper.type = lcf.type();
		
		// can't have more than one marker for a given type
		if (usedTypes.contains(newWrapper.type))
		{
			logger.log(Type.ERROR, "Multiple localization markers for type " + newWrapper.type);
			throw new UnableToCompleteException();
		}
		
		out.add(newWrapper);
		usedTypes.add(newWrapper.type);
	}
	
	private List<WrapperData> resolveWrapperData(JClassType interfaceType, JField jField, TreeLogger logger) throws UnableToCompleteException
	{
		List<WrapperData> out = new ArrayList<>();
		JParameter[] methodParams = extractInterfaceMethodParams(interfaceType);
		String parentClassName = methodParams[0].getType().getSimpleSourceName();
		String fieldName = jField.getName();
		
		// allow multiple markers on the same field (when localizing multiple properties of a single field)
		Set<LocalizedFieldType> usedWrapperTypes = new HashSet<>();
		for (Annotation anno : jField.getAnnotations())
		{
			if (anno instanceof LocalizedField)
				generateLocalizedFieldWrapperData((LocalizedField)anno, parentClassName, fieldName, out, usedWrapperTypes, logger);
			else if (anno instanceof LocalizedFieldContainer)
			{
				for (LocalizedField itr : ((LocalizedFieldContainer)anno).value())
					generateLocalizedFieldWrapperData(itr, parentClassName, fieldName, out, usedWrapperTypes, logger);
			}
			else if (anno instanceof LocalizedCommonField)
				generateLocalizedCommonFieldWrapperData((LocalizedCommonField)anno, out, usedWrapperTypes, logger);
			else if (anno instanceof LocalizedCommonFieldContainer)
			{
				for (LocalizedCommonField itr : ((LocalizedCommonFieldContainer)anno).value())
					generateLocalizedCommonFieldWrapperData(itr, out, usedWrapperTypes, logger);
			}
		}
				
		return out;
	}
	
	private void writeFieldsBinding(JClassType interfaceType, IndentedWriter writer, TreeLogger logger) throws UnableToCompleteException
	{
		for (JField jField : interfaceType.getEnclosingType().getFields())
		{
			List<WrapperData> markers = resolveWrapperData(interfaceType, jField, logger);
			int i = 1;
			for (WrapperData wd : markers)
			{
				String wrapperName = jField.getName() + "_Wrapper" + i;
				StringBuilder wrapperSB = new StringBuilder("SimpleLocalizedWidget<?> ");
				wrapperSB.append(wrapperName).append(" = new ");
									
				switch (wd.type)
				{
				case TEXT_BUTTON:
					wrapperSB.append(SimpleLocalizedTextButtonWidget.class.getSimpleName());
					break;
				case LISTBOX_OPTION:
					wrapperSB.append(SimpleLocalizedListBoxOptionWidget.class.getSimpleName());
					break;
				case TEXT_BASIC:
					wrapperSB.append(SimpleLocalizedTextWidget.class.getSimpleName());
					break;
				case TOOLTIP_MATERIAL:
					wrapperSB.append(SimpleLocalizedTooltipWidget.class.getSimpleName());
					break;
				case TEXT_TITLE:
				case TEXT_DESCRIPTION:
					wrapperSB.append(SimpleLocalizedTitleWidget.class.getSimpleName());
					break;
				case TOOLTIP_BASIC:
					wrapperSB.append(SimpleLocalizedAltTextWidget.class.getSimpleName());
					break;
				case TEXTBOX_LABEL:
				case TEXTBOX_PLACEHOLDER:
					wrapperSB.append(SimpleLocalizedTextBoxWidget.class.getSimpleName());
					break;
				case TEXT_NAVBRAND:
					wrapperSB.append(SimpleLocalizedNavBrandWidget.class.getSimpleName());
					break;
				default:
					logger.log(Type.ERROR, "Missing implementation for LocalizedFieldType " + wd.type);
					throw new UnableToCompleteException();
				}
				
				wrapperSB.append("<>(owner.").append(jField.getName()).append(", \"")
						.append(wd.provider).append("\", \"")
						.append(wd.tag).append("\"");
				
				switch (wd.type)
				{
				case TEXT_TITLE:
					wrapperSB.append(", false");
					break;
				case TEXT_DESCRIPTION:
					wrapperSB.append(", true");
					break;
				case TEXTBOX_LABEL:
					wrapperSB.append(", false");
					break;
				case TEXTBOX_PLACEHOLDER:
					wrapperSB.append(", true");
					break;
				}

				wrapperSB.append(");");
				writer.write(wrapperSB.toString());
				writer.write("out.localizationWrappers.add(%1$s);\n", wrapperName);
			//	writer.write("GWT.log(\"Adding localization wrapper: %1$s of Type: %2$s\",null);", jField.getName(), jField.getType().getSimpleSourceName());
				
				i++;
			}
		}
	}

	private void writeMethodIntro(IndentedWriter writer, JParameter[] parameters)
	{
		String parentClassName = parameters[0].getType().getSimpleSourceName();
		writer.write("public LocalizationBinderData bind(%1$s owner) {", parentClassName);
		writer.indent();
		writer.write("LocalizationBinderData out = new LocalizationBinderData(\"%1$s\");", parentClassName);
	}
	
	private void writeMethodOutro(IndentedWriter writer)
	{
		writer.write("return out;");
		writer.outdent();
		writer.write("}");
	}

	private void writeFieldsIntro(IndentedWriter writer)
	{
		// nothing to do now
		writer.newline();
	}

	private void writeImports(IndentedWriter writer, JParameter[] parameters, TypeOracle oracle)
	{
		writer.write(IMPORT, GWT.class.getName());
		writer.write(IMPORT, parameters[0].getType().getQualifiedSourceName());		// enclosing scope
		writer.write(IMPORT, LocalizationBinderData.class.getName());
		writer.write(IMPORT, SimpleLocalizedWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTextWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedListBoxOptionWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTextButtonWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTooltipWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTitleWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedAltTextWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTextBoxWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedNavBrandWidget.class.getName());
		writer.newline();
	}

}
