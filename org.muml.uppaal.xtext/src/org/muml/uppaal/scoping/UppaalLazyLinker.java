package org.muml.uppaal.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.muml.uppaal.NTA;

public class UppaalLazyLinker extends LazyLinker {
	@Override
	protected void beforeModelLinked(EObject model, IDiagnosticConsumer diagnosticConsumer) {
		super.beforeModelLinked(model, diagnosticConsumer);
		
		if (model instanceof NTA) {
			NTA nta = (NTA) model;
			nta.setName("Parsed NTA");
		}
	}
}
