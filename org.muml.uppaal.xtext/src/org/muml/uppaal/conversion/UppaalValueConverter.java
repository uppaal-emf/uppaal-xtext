package org.muml.uppaal.conversion;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.muml.uppaal.queries.PathType;

public class UppaalValueConverter extends DefaultTerminalConverters {
	public UppaalValueConverter() {
		super();
	}

	@ValueConverter(rule = "QUOTED_INT")
	public IValueConverter<Integer> QUOTED_INT() {
		return new IValueConverter<Integer>() {

			public String toString(Integer value) {
				return "\"" + value + "\"";
			}

			public Integer toValue(String string, INode node) throws ValueConverterException {

				if (string == null || string.length() <= 2) {
					throw new ValueConverterException("Only integer values are allowed.", node, null);
				}

				// Trim quotes.
				string = string.substring(1, string.length() - 1).trim();

				// Check format.
				if (!string.matches("-?[0-9]+")) {
					throw new ValueConverterException("Only integer values are allowed.", node, null);
				}

				return Integer.valueOf(string);
			}

		};

	}

	// This is required, due to a hack that aims to make comments like: A friend's
	// house
	// parsable. The reason why it isn't is explained in the grammar rule.
	@ValueConverter(rule = "QUERY_COMMENT")
	public IValueConverter<String> QUERY_COMMENT() {
		return new IValueConverter<String>() {
			public String toString(String comment) {
				return "<comment>" + comment + "</comment>";
			}

			public String toValue(String comment, INode node) {
				String prefix = "<comment>";
				String suffix = "</comment>";

				String extracted = comment.substring(prefix.length(), comment.length() - suffix.length());
				return extracted;
			}
		};
	}

	@ValueConverter(rule = "PathType")
	public IValueConverter<PathType> PathType() {
		return new IValueConverter<PathType>() {
			public String toString(PathType op) {
				switch (op) {
				case FUTURE:
					return "&lt;&gt;";
				case GLOBAL:
					return "[]";
				default:
					throw new ValueConverterException("Unknown temporal statement", null, null);
				}
			}

			public PathType toValue(String string, INode node) {
				switch (string) {
				case "&lt;&gt;":
					return PathType.FUTURE;
				case "[]":
					return PathType.GLOBAL;
				default:
					throw new ValueConverterException("Can't convert given value to a temporal statement.", node, null);
				}

			}
		};
	}

	@ValueConverter(rule = "ChannelPrefixExpression_Urgent")
	public IValueConverter<Boolean> ChannelPrefixExpression_Urgent() {
		return new IValueConverter<Boolean>() {
			public String toString(Boolean value) {
				return (value.booleanValue()) ? "urgent" : "";
			}

			public Boolean toValue(String string, INode node) throws ValueConverterException {
				return string.equals("urgent");
			}
		};
	}

	@ValueConverter(rule = "ChannelPrefixExpression_Broadcast")
	public IValueConverter<Boolean> ChannelPrefixExpression_Broadcast() {
		return new IValueConverter<Boolean>() {
			public String toString(Boolean value) {
				return (value.booleanValue()) ? "broadcast" : "";
			}

			public Boolean toValue(String string, INode node) throws ValueConverterException {
				return string.equals("broadcast");
			}
		};
	}
}
