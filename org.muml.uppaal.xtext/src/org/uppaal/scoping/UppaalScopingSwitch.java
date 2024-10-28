package org.uppaal.scoping;

import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.uppaal.model.NTA;
import org.uppaal.model.declarations.Function;
import org.uppaal.model.declarations.SystemDeclarations;
import org.uppaal.model.declarations.TypeDeclaration;
import org.uppaal.model.declarations.TypedElementContainer;
import org.uppaal.model.declarations.util.DeclarationsSwitch;
import org.uppaal.model.expressions.DataPrefixExpression;
import org.uppaal.model.expressions.FunctionCallExpression;
import org.uppaal.model.expressions.IdentifierExpression;
import org.uppaal.model.expressions.QuantificationExpression;
import org.uppaal.model.expressions.ScopedIdentifierExpression;
import org.uppaal.model.expressions.util.ExpressionsSwitch;
import org.uppaal.model.statements.Block;
import org.uppaal.model.statements.Iteration;
import org.uppaal.model.statements.util.StatementsSwitch;
import org.uppaal.model.templates.Edge;
import org.uppaal.model.templates.Template;
import org.uppaal.model.templates.util.TemplatesSwitch;
import org.uppaal.model.util.UppaalSwitch;

public class UppaalScopingSwitch<T> extends ComposedSwitch<T> {
	private final DeclarationsSwitch<T> declarationsSwitch = new DeclarationsSwitch<>() {
		@Override
		public T caseSystemDeclarations(SystemDeclarations declarations) {
			return handleCase(declarations);
		}
		
		@Override
		public T caseFunction(Function function) {
			return handleCase(function);
		}
		
		@Override
		public T caseTypeDeclaration(TypeDeclaration declaration) {
			return handleCase(declaration);
		}
		
		@Override
		public T caseTypedElementContainer(TypedElementContainer container) {
			return handleCase(container);
		}
	};
	
	private final StatementsSwitch<T> statementsSwitch = new StatementsSwitch<>() {
		@Override
		public T caseBlock(Block block) {
			return handleCase(block);
		}
		
		@Override
		public T caseIteration(Iteration iteration) {
			return handleCase(iteration);
		}
	};
	
	private final TemplatesSwitch<T> templateSwitch = new TemplatesSwitch<>() {
		@Override
		public T caseTemplate(Template template) {
			return handleCase(template);
		}
		
		@Override
		public T caseEdge(Edge edge) {
			return handleCase(edge);
		}
	};
	
	private final UppaalSwitch<T> uppaalSwitch = new UppaalSwitch<>() {
		@Override
		public T caseNTA(NTA nta) {
			return handleCase(nta);
		}
	};
	
	private final ExpressionsSwitch<T> expressionSwitch = new ExpressionsSwitch<>() {
		@Override
		public T caseQuantificationExpression(QuantificationExpression expression) {
			return handleCase(expression);
		}
		
		@Override
		public T caseDataPrefixExpression(DataPrefixExpression expression) {
			return handleCase(expression);
		}
		
		@Override
		public T caseIdentifierExpression(IdentifierExpression expression) {
			return handleCase(expression);
		}
		
		@Override
		public T caseScopedIdentifierExpression(ScopedIdentifierExpression expression) {
			return handleCase(expression);
		}
		
		@Override
		public T caseFunctionCallExpression(FunctionCallExpression expression) {
			return handleCase(expression);
		}
	};
	
	public UppaalScopingSwitch() {
		addSwitch(uppaalSwitch);
		addSwitch(templateSwitch);
		addSwitch(statementsSwitch);
		addSwitch(declarationsSwitch);
		addSwitch(expressionSwitch);
	}
	
	public T handleCase(Iteration iteration) {
		return defaultCase(iteration);
	}

	public T handleCase(Block block) {
		return defaultCase(block);
	}

	public T handleCase(Function function) {
		return defaultCase(function);
	}

	public T handleCase(SystemDeclarations declarations) {
		return defaultCase(declarations);
	}

	public T handleCase(NTA nta) {
		return defaultCase(nta);
	}
	
	public T handleCase(Template template) {
		return defaultCase(template);
	}
	
	public T handleCase(Edge edge) {
		return defaultCase(edge);
	}

	public T handleCase(QuantificationExpression expression) {
		return defaultCase(expression);
	}

	public T handleCase(TypeDeclaration declaration) {
		return defaultCase(declaration);
	}

	public T handleCase(TypedElementContainer container) {
		return defaultCase(container);
	}

	public T handleCase(DataPrefixExpression expression) {
		return defaultCase(expression);
	}

	public T handleCase(IdentifierExpression expression) {
		return defaultCase(expression);
	}

	public T handleCase(ScopedIdentifierExpression expression) {
		return defaultCase(expression);
	}

	public T handleCase(FunctionCallExpression expression) {
		return defaultCase(expression);
	}
}
