/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPTaglibVariableCheck extends BaseJSPTermsCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		return _formatTaglibVariable(fileName, content);
	}

	private String _formatTaglibVariable(String fileName, String content)
		throws IOException {

		Matcher matcher = _taglibVariablePattern.matcher(content);

		while (matcher.find()) {
			int position = matcher.start(1);

			String s = matcher.group(1);

			String[] variableDefiniations = s.split(";\n");

			String nextTag = matcher.group(7);

			for (String variableDefiniation : variableDefiniations) {
				position = position + variableDefiniation.length() + 2;

				String[] array = variableDefiniation.split(" = ", 2);

				String taglibValue = array[1];

				int i = array[0].lastIndexOf(CharPool.SPACE);

				String variableName = array[0].substring(i + 1);

				if (_hasVariableReference(
						content.substring(position), variableName,
						taglibValue)) {

					continue;
				}

				if (!taglibValue.contains("\n") &&
					(taglibValue.contains("\\\"") ||
					 (taglibValue.contains(StringPool.APOSTROPHE) &&
					  taglibValue.contains(StringPool.QUOTE)))) {

					if (!variableName.startsWith("taglib") &&
						(_getVariableCount(content, variableName) == 2) &&
						nextTag.contains("=\"<%= " + variableName + " %>\"")) {

						addMessage(
							fileName,
							"Variable '" + variableName +
								"' should start with 'taglib'",
							getLineNumber(content, matcher.start(1)));
					}

					continue;
				}

				if (nextTag.contains("=\"<%= " + variableName + " %>\"")) {
					populateContentsMap(fileName, content);

					String newContent = null;

					if (taglibValue.startsWith("{")) {
						String typeName = StringUtil.trimLeading(
							array[0].substring(0, i));

						if (typeName.endsWith("[][]") ||
							!typeName.endsWith("[]")) {

							continue;
						}

						newContent = StringUtil.replaceFirst(
							content, "<%= " + variableName + " %>\"",
							StringBundler.concat(
								"<%= new ", typeName, " ", taglibValue,
								" %>\""),
							matcher.start(7));
					}
					else {
						newContent = StringUtil.replaceFirst(
							content, "<%= " + variableName + " %>\"",
							"<%= " + taglibValue + " %>\"", matcher.start(7));
					}

					Set<String> checkedFileNames = new HashSet<>();
					Set<String> includeFileNames = new HashSet<>();

					if (hasUnusedJSPTerm(
							fileName, newContent, "\\W" + variableName + "\\W",
							"variable", checkedFileNames, includeFileNames,
							getContentsMap())) {

						if (!taglibValue.contains("\n")) {
							return StringUtil.replaceFirst(
								newContent, variableDefiniation + ";\n",
								StringPool.BLANK, matcher.start());
						}

						addMessage(
							fileName,
							StringBundler.concat(
								"No need to declare variable '", variableName,
								"', inline inside the tag."),
							getLineNumber(content, matcher.start(3)));
					}
				}
			}
		}

		return content;
	}

	private int _getVariableCount(String content, String variableName) {
		int count = 0;

		Pattern pattern = Pattern.compile("\\W" + variableName + "\\W");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.start() + 1;

			if (JSPSourceUtil.isJavaSource(content, x)) {
				if (!ToolsUtil.isInsideQuotes(content, x)) {
					count++;
				}

				continue;
			}

			if (JSPSourceUtil.isJavaSource(content, x, true)) {
				count++;
			}
		}

		return count;
	}

	private boolean _hasVariableReference(
		String content, String variableName, String taglibValue) {

		int endPosition = content.lastIndexOf(
			"=\"<%= " + variableName + " %>\"");

		if (endPosition == -1) {
			return false;
		}

		boolean hasVariableReference = false;

		endPosition = content.indexOf("\n", endPosition);

		Matcher matcher1 = _methodCallPattern.matcher(taglibValue);

		outerLoop:
		while (matcher1.find()) {
			Pattern pattern = Pattern.compile(
				"\\b(?<!['\"])" + matcher1.group(1) + "\\.(\\w+)?\\(");

			Matcher matcher2 = pattern.matcher(content);

			while (matcher2.find()) {
				if (matcher2.start() > endPosition) {
					hasVariableReference = false;

					continue outerLoop;
				}

				String methodName = matcher2.group(1);

				if (!methodName.startsWith("get") &&
					!methodName.startsWith("is")) {

					return true;
				}

				hasVariableReference = false;
			}
		}

		return hasVariableReference;
	}

	private static final Pattern _methodCallPattern = Pattern.compile(
		"\\b(?<!['\"])([a-z]\\w+)\\.(\\w+)?\\(");
	private static final Pattern _taglibVariablePattern = Pattern.compile(
		"\n((\t*([\\w<>\\[\\],\\? ]+) (\\w+) = (((?!;\n).)*);\n)+)\\s*%>\n+" +
			"((\n\t*)<(([^\n]+/>)|([\\S\\s]*?\\8((</)|(/>))\\S*)))(\n|\\Z)",
		Pattern.DOTALL);

}