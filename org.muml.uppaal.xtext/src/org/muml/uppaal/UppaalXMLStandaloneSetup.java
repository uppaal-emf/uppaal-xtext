/*
 * generated by Xtext 2.28.0
 */
package org.muml.uppaal;


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class UppaalXMLStandaloneSetup extends UppaalXMLStandaloneSetupGenerated {

	public static void doSetup() {
		new UppaalXMLStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
