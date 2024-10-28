package org.uppaal.scoping;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.uppaal.core.NamedElement;
import org.uppaal.templates.Edge;
import org.uppaal.templates.Location;
import org.uppaal.types.Type;

/**
 * Extracts a {@link org.eclipse.xtext.naming.QualifiedName} for some
 * classes from the uppaal meta model.
 */
public class UppaalQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {
	
	/**
	 * Uses the unique id for elements that do have such an id.
	 * @param element The element to get the id from.
	 * @return The id as a qualified name.
	 */
	QualifiedName qualifiedName(Location element) {
		return QualifiedName.create(element.getId());
	}
	
	/**
	 * Uses the unique id for elements that do have such an id.
	 * @param element The element to get the id from.
	 * @return The id as a qualified name.
	 */
	QualifiedName qualifiedName(Edge element) {
		return QualifiedName.create(element.getId());
	}
	
	/**
	 * Uses the types name as a qualified name.
	 * @param element The type to get the name from.
	 * @return The type name as a qualified name.
	 */
	QualifiedName qualifiedName(Type t) {
        return QualifiedName.create(t.getName());
    }
	
	/**
	 * Uses the elements name as a qualified name.
	 * @param element The element to get the name from.
	 * @return The elements name as a qualified name.
	 */
	QualifiedName qualifiedName(NamedElement n) {
		return QualifiedName.create(n.getName());
	}

}
