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
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.wrappers.SimpleLocalizedAltTextWidget;
import com.flair.client.localization.wrappers.SimpleLocalizedListBoxOptionWidget;
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
	
	private List<WrapperData> resolveWrapperData(JClassType interfaceType, JField jField, TreeLogger logger) throws UnableToCompleteException
	{
		List<WrapperData> out = new ArrayList<>();
		JParameter[] methodParams = extractInterfaceMethodParams(interfaceType);
		String parentClassName = methodParams[0].getType().getQualifiedSourceName();
		
		// allow multiple markers on the same field (when localizing multiple properties of a single field)
		Set<LocalizedFieldType> usedWrapperTypes = new HashSet<>();
		for (Annotation anno : jField.getDeclaredAnnotations())
		{
			WrapperData newWrapper = new WrapperData();
			boolean add = false;
			if (anno instanceof LocalizedField)
			{
				LocalizedField lf = (LocalizedField)anno;
				newWrapper.provider = lf.provider();
				newWrapper.tag = lf.tag();
				newWrapper.type = lf.type();
				
				// use the parent class's name as the provider and the field name as the tag by default
				if (newWrapper.provider.isEmpty())
					newWrapper.provider = parentClassName;
				
				if (newWrapper.tag.isEmpty())
					newWrapper.tag = jField.getName();
				
				add = true;
			}
			else if (anno instanceof LocalizedCommonField)
			{
				LocalizedCommonField lcf = (LocalizedCommonField)anno;
				newWrapper.provider = DefaultLocalizationProviders.COMMON.toString();
				newWrapper.tag = lcf.tag().toString();
				newWrapper.type = lcf.type();
				
				add = true;
			}
			
			if (add)
			{
				// can't have more than one marker for a given type
				if (usedWrapperTypes.contains(newWrapper.type))
				{
					logger.log(Type.ERROR, "Multiple localization markers for type " + newWrapper.type);
					throw new UnableToCompleteException();
				}
				
				out.add(newWrapper);
				usedWrapperTypes.add(newWrapper.type);
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
				case BUTTON:
					wrapperSB.append(SimpleLocalizedTextButtonWidget.class.getSimpleName());
					break;
				case LISTBOX_OPTION:
					wrapperSB.append(SimpleLocalizedListBoxOptionWidget.class.getSimpleName());
					break;
				case TEXT:
					wrapperSB.append(SimpleLocalizedTextWidget.class.getSimpleName());
					break;
				case TOOLTIP:
					wrapperSB.append(SimpleLocalizedTooltipWidget.class.getSimpleName());
					break;
				case TITLE:
				case DESCRIPTION:
					wrapperSB.append(SimpleLocalizedTitleWidget.class.getSimpleName());
					break;
				case ALT_TEXT:
					wrapperSB.append(SimpleLocalizedAltTextWidget.class.getSimpleName());
					break;
				default:
					logger.log(Type.ERROR, "Missing implementation for LocalizedFieldType " + wd.type);
					throw new UnableToCompleteException();
				}
				
				wrapperSB.append("<>(owner.").append(jField.getName()).append(", ")
						.append(wd.provider).append(", ")
						.append(wd.tag).append(wd.type == LocalizedFieldType.DESCRIPTION ? ", true" : "")
						.append(");");
				writer.write(wrapperSB.toString());
				writer.write("out.add(%1$s);\n", wrapperName);
				writer.write("GWT.log(\"Adding localization wrapper: %1$s of Type: %2$s\",null);", jField.getName(),
						jField.getType().getQualifiedSourceName());
				
				i++;
			}
		}
	}

	private void writeMethodIntro(IndentedWriter writer, JParameter[] parameters)
	{
		String parentClassName = parameters[0].getType().getQualifiedSourceName();
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
		writer.write(IMPORT, LocalizationBinderData.class.getName());
		writer.write(IMPORT, SimpleLocalizedWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTextWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedListBoxOptionWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTextButtonWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTooltipWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedTitleWidget.class.getName());
		writer.write(IMPORT, SimpleLocalizedAltTextWidget.class.getName());
		writer.newline();
	}

}
