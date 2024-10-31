package org.uppaal.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultTransientValueService;
import org.uppaal.UppaalPackage;
import org.uppaal.core.CorePackage;
import org.uppaal.templates.TemplatesPackage;

public class UppaalTransientValueService extends DefaultTransientValueService {
	public UppaalTransientValueService() {
		super();
	}
	
	@Override
	public boolean isTransient(EObject owner, EStructuralFeature feature, int index) {
		if (UppaalPackage.Literals.NTA.isInstance(owner)) {
			if (feature == CorePackage.Literals.NAMED_ELEMENT__NAME) {
				return true;
			}
		}
		
		if (TemplatesPackage.Literals.LOCATION.isInstance(owner)) {
			if (feature == TemplatesPackage.Literals.LOCATION__INCOMING_EDGES) {
				return true;
			}
			if (feature == TemplatesPackage.Literals.LOCATION__OUTGOING_EDGES) {
				return true;
			}
		}
		
		return super.isTransient(owner, feature, index);
	}
}
