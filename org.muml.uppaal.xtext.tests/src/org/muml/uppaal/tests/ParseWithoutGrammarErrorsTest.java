package org.muml.uppaal.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.muml.uppaal.UppaalPackage;

import com.google.inject.Inject;

@ExtendWith(InjectionExtension.class)
@InjectWith(UppaalXMLInjectorProvider.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ParseWithoutGrammarErrorsTest {
	
	/* Path to test resources */
	private static final Path TESTDIR_PATH = Path.of("data/demo-new");
	private static final PathMatcher XML_MATCHER = FileSystems.getDefault().getPathMatcher("glob:**.{xml,XML}");
		
	@Inject
	private XtextResourceSet resourceSet;
	
	/* Files to test with */
	private Stream<Named<Path>> testFiles;
	
	@BeforeAll
	public void setUp() throws IOException {
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, true);
		resourceSet.setClasspathURIContext(getClass());

		testFiles = Files.walk(TESTDIR_PATH)
			.filter(Files::isReadable)
			.filter(Files::isRegularFile)
			.filter(p -> XML_MATCHER.matches(p))
			.map(path -> Named.of(path.getFileName().toString(), path));
	}
	
	@AfterAll
	public void tearDown() {
		testFiles.close();
	}
	
    @TestFactory
    public Stream<DynamicTest> runTestsFromDirectory() throws IOException {
    	return stream(testFiles, path -> {
    		// load model
    		URI fileUri = URI.createFileURI(path.toAbsolutePath().toString());
    		Resource resource = resourceSet.getResource(fileUri, true);
    					
			// assert correct type
    		Object nta = EcoreUtil.getObjectByType(resource.getContents(), UppaalPackage.Literals.NTA);
			assertNotNull(nta, "Could not be parsed to an instance of NTA.");
			
			// check for errors
			EList<Diagnostic> errors = resource.getErrors();
			assertTrue(errors.isEmpty(), summarize(errors));
    	});
    }
    
    private static String summarize(EList<Diagnostic> errors) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%d errors while parsing:\n", errors.size()));
		
		Map<Class<? extends Diagnostic>, List<Diagnostic>> mappedErrors
			= errors.stream().collect(Collectors.groupingBy(Diagnostic::getClass));
		
		for (Class<? extends Diagnostic> errorClass : mappedErrors.keySet()) {
			sb.append(errorClass.getSimpleName());
			sb.append(":\n");
			
			for (Diagnostic error : mappedErrors.get(errorClass)) {
				sb.append(String.format("[%s:%s:%s]\t%s\n",
						error.getLocation(),
						error.getLine(),
						error.getColumn(),
						error.getMessage())
				);
			}
		}
		
		return sb.toString();
	}
}
