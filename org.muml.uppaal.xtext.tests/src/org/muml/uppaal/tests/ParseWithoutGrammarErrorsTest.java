package org.muml.uppaal.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.linking.impl.XtextLinkingDiagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.muml.uppaal.UppaalXMLStandaloneSetup;

import com.google.inject.Injector;

@ExtendWith(InjectionExtension.class)
@InjectWith(UppaalXMLInjectorProvider.class)
public class ParseWithoutGrammarErrorsTest {

    private Injector injector;

    public void setup() {
    	UppaalXMLStandaloneSetup.doSetup(); 
    	injector = new UppaalXMLInjectorProvider().getInjector();    	
    }

    private Resource loadObjectModel(URI uri) {
		XtextResourceSet set = injector.getInstance(XtextResourceSet.class);
		
		set.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		
		System.err.println("set: " + set + ", uri: " + uri);
		
		Resource res = set.getResource(uri,  true);
		
		
        return res;
    }

    private static URI pathTo(String name) {
		String pwd = System.getProperty("user.dir");
		String base = "/data/demo-new/";
		URI uri = URI.createFileURI(pwd + base + name);
        return uri;
    }
    
    private void assertEQ(int expected, int actual, String prefix) {
    	if (expected != actual) {
    		throw new AssertionError(prefix + ": expected were'" + expected + "' but got '" + actual + "'");
    	}
    }
    
    public static Iterable<? extends Object[]> data() {
        return Arrays.asList(new Object[][] {
            //  name of the file to pa            The expected amount of syntax errors (Subject to change: aim for 0)
            //  ^                                 ^ 
            //  |                                 |   The expected amount of linking errors (Subject to change: aim for 0)
            //  |                                 |   ^ 
            { ("2doors.xml")                   ,  0, 30 },
            { ("bridge.xml")                   ,  1, 20 },
            { ("fischer_symmetry.xml")         ,  0,  0 },
            { ("fischer.xml")                  ,  0,  0 },
            { ("interrupt.xml")                ,  3,  2 },
            { ("lsc_example.xml")              ,  3,  0 },
            { ("lsc_train-gate_parameters.xml"),  9,  1 },
            { ("scheduling3.xml")              , 16,  7 },
            { ("scheduling4.xml")              , 35,  3 },
            { ("SchedulingFramework.xml")      , 18, 42 },
            { ("train-gate-orig.xml")          ,  7, 41 },
            { ("train-gate.xml")               ,  4,  6 },
        });
    }

    private void parserErrorsBelowBoundsInFile(String fileName, int expectedSyntaxErrorCount) {
    	URI fileToParse = pathTo(fileName);
        Resource res = loadObjectModel(fileToParse);

        Assertions.assertNotNull(res);

		EList<Diagnostic> errors = res.getErrors();
		List<Diagnostic> syntaxErrors = errors.stream()
				.filter(e -> e instanceof XtextSyntaxDiagnostic)
				.collect(Collectors.toList());
		
		for (Diagnostic d : syntaxErrors) {
			System.out.println(d.getMessage());
		}
		
		int actualSyntaxErrorCount = syntaxErrors.size();
		
		assertEQ(expectedSyntaxErrorCount, actualSyntaxErrorCount, fileName + "-linking");
    }
    
    private void linkerErrorsBelowBoundsInFile(String fileName, int expectedlinkingErrorCount) {
    	URI fileToParse = pathTo(fileName);
        Resource res = loadObjectModel(fileToParse);

        Assertions.assertNotNull(res);

		EList<Diagnostic> errors = res.getErrors();

		List<Diagnostic> linkingErrors = errors.stream()
				.filter(e -> e instanceof XtextLinkingDiagnostic)
				.collect(Collectors.toList());
		
		int actualLinkingErrorCount = linkingErrors.size();
		
		assertEQ(expectedlinkingErrorCount, actualLinkingErrorCount, fileName + "-linking");
    }
    
    @Test
    public void parserErrorsMatch() {
    	setup();
    	for(Object[] row : data()) {
    		System.out.println((String)row[0]);
    		parserErrorsBelowBoundsInFile((String)row[0], (int)row[1]);
    	}
    }
    
    @Test
    public void linkerErrorsMatch() {
    	setup();
    	for(Object[] row : data()) {
    		linkerErrorsBelowBoundsInFile((String)row[0], (int)row[2]);
    	}
    }
}
