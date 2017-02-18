package app;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.etl.EtlModule;

import NeoEpsilon.Box;
import NeoEpsilon.NeoEpsilonFactory;
import NeoEpsilon.NeoEpsilonPackage;
import NeoEpsilon.Shelf;
import fr.inria.atlanmod.neoemf.datastore.PersistenceBackendFactoryRegistry;
import fr.inria.atlanmod.neoemf.graph.blueprints.datastore.BlueprintsPersistenceBackendFactory;
import fr.inria.atlanmod.neoemf.graph.blueprints.neo4j.option.BlueprintsNeo4jOptionsBuilder;
import fr.inria.atlanmod.neoemf.graph.blueprints.util.BlueprintsURI;
import fr.inria.atlanmod.neoemf.resource.PersistentResource;
import fr.inria.atlanmod.neoemf.resource.PersistentResourceFactory;

public class App {

	public static void main(String[] args) throws Exception {
		App app = new App();
		app.run();
		System.exit(0);
	}
	public void run() throws Exception {
		NeoEpsilonPackage.eINSTANCE.eClass();
		//run three processing modes on XMI, results are correct
		process("xmi", "epsilon");
		process("xmi", "api");
		process("xmi", "head2head");

		//run three processing modes on NeoEMF, results appear to be lacking
		process("neo", "epsilon");
		process("neo", "api");
		process("neo", "head2head");

	}

	//execute a mode of processing on a resource of a given type 
	private void process(String type, String mode) throws Exception, IOException {
		System.out.println("*** Running mode "+mode+" on "+type+" *** ");
		String location = "instance/"+mode+"/model."+type;
		cleanUp(location);
		Resource resource = getResource(location, type);
		switch (mode) {
		case "epsilon":
			runEpsilon(resource);
			break;
		case "api":
			runAPIs(resource);
			break;
		case "head2head":
			runHeadToHeadAPIComparison(resource);
			break;
		default:
			System.err.println("Unknown mode...");
			break;
		}
		
		//create an XMI backup for NeoEMF resources for comparison
		if (type.equals("neo")) {
			Resource backupResource = getResource(location+".xmi", "xmi");
			backupResource.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));
			backupResource.save(Collections.emptyMap());
		}

		resource.save(Collections.emptyMap());
		closeResource(resource);
	}

	//example manipulation with Epsilon
	private void runEpsilon(Resource resource) throws Exception {
		EmfModel model = getModel("NeoEpsilon", resource);

		String source = "epsilon/neo.eol";

		IEolExecutableModule module = loadModule(source);
		module.getContext().getModelRepository().addModel(model);
		module.execute();

		module.reset();
	}

	//use Java and Epsilon EMF API for the same purpose 
	private void runAPIs(Resource resource) throws Exception {
		//create top level container with Java EMF API
		Shelf shelf = NeoEpsilonFactory.eINSTANCE.createShelf();
		shelf.setName("Shelf");
		resource.getContents().add(shelf);
		
		//NOTE: creating elements with Java EMF API works 
		Box javaBox = NeoEpsilonFactory.eINSTANCE.createBox();
		javaBox.setName("JavaBox");
		((Shelf)resource.getContents().get(0)).getBoxes().add(javaBox);
		
		EmfModel model = getModel("NeoEpsilon", resource);
		//NOTE: creating elements with Epsilon EMF API does not work
		Box epsilonBox = (Box) model.createInstance("Box");
		epsilonBox.setName("EpsilonBox");
		List<EObject> shelves = (List<EObject>) model.getAllOfType("Shelf");
		((Shelf) shelves.get(0)).getBoxes().add(epsilonBox);

		//list all Box objects on the shelf
		System.out.println("Checking Shelves and Boxes...");
		for (EObject s : shelves) {
			for (Box b : ((Shelf) s).getBoxes()) {
				System.out.println("  " +b.getName());
			}
		}

		//list all Box objects
		System.out.println("Checking All Boxes...");
		Collection<EObject> boxes = model.getAllOfType("Box");
		for (EObject b : boxes) {
			System.out.println("--"+((Box) b).getName());
		}

		//NOTE: creating top level elements with Epsilon EMF API does seem to work
		Shelf epsilonShelf = (Shelf) model.createInstance("Shelf");
		epsilonShelf.setName("EpsilonShelf");
		System.out.println("Checking All Shelves...");
		for (EObject s : model.getAllOfType("Shelf")) {
			System.out.println("  "+((Shelf) s).getName());
		}		
	}
	
	//Head-to-head comparison (run in debug mode to investigate
	private void runHeadToHeadAPIComparison(Resource resource) throws Exception {
		//create top level container with Java EMF API
		Shelf shelf = NeoEpsilonFactory.eINSTANCE.createShelf();
		shelf.setName("Shelf");
		resource.getContents().add(shelf);

		EmfModel model = getModel("NeoEpsilon", resource);
		//NOTE: head to head comparison:
		// - only difference appears to be under eStorage where
		//   epsilon appears to add extra information which 
		//   potentially prevents NeoEMF from working
		//Run in debugging mode to investigate
		Box javaBox = NeoEpsilonFactory.eINSTANCE.createBox();
		Box epsilonBox = (Box) model.createInstance("Box");

		javaBox.setName("JavaBox");
		epsilonBox.setName("EpsilonBox");

		((Shelf)resource.getContents().get(0)).getBoxes().add(javaBox);
		
		List<EObject> shelves = (List<EObject>) model.getAllOfType("Shelf");
		((Shelf) shelves.get(0)).getBoxes().add(epsilonBox);

	}
	
	//Supporting methods for resource handling
	
	private EmfModel getModel(String alias, Resource resource) {
		EmfModel model = new InMemoryEmfModel(alias, resource);
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(true);
		model.setCachingEnabled(true);
		return model;
	}

	
	private IEolExecutableModule loadModule(String source) throws Exception {
		IEolExecutableModule module = null;
		if (source.endsWith("etl")) {
			module = new EtlModule();	
		} else if (source.endsWith("eol")) {
			module = new EolModule();
		} else {
			System.err.println("Unknown module type...");
		}
		
		module.parse(new File(source));
	
		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
		}
		return module;
	}
	
	private Resource getResource(String location, String type) throws Exception {
		Resource resource = null;
		switch (type) {
		case "xmi":
			resource = createXMIResource(location);
			break;
		case "neo":
			resource = createNeoResource(location);
			break;
		default:
			System.err.println("Unknown resource type...");
			throw new Exception();
		}
		return resource;
	}
	
	private void cleanUp(String location) throws IOException {
		File target = new File(location);
		if (target.exists()) {
			if (target.isDirectory()) {
				FileUtils.deleteDirectory(target);
			} else if (target.isFile()) {
				FileUtils.deleteQuietly(target);
			} else {
				System.err.println("Unknown resource type...");
			}
		}
	}

	private Resource createXMIResource(String location) throws IOException {
	    ResourceSet rSet = new ResourceSetImpl();
	    String extension = location.substring(location.lastIndexOf(".")+1);
	    rSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(extension, new XMIResourceFactoryImpl());
		Resource resource = rSet.createResource(URI.createURI(location));
		resource.save(Collections.emptyMap());
		return resource;
		
	}
	
	private Resource createNeoResource(String location) throws IOException {
	    PersistenceBackendFactoryRegistry.register(BlueprintsURI.SCHEME, BlueprintsPersistenceBackendFactory.getInstance());
	    ResourceSet rSet = new ResourceSetImpl();
	    rSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(BlueprintsURI.SCHEME, PersistentResourceFactory.getInstance());
	
	    PersistentResource resource = (PersistentResource) rSet.createResource(BlueprintsURI.createFileURI(new File(location)));
        Map<String, Object> options = BlueprintsNeo4jOptionsBuilder.newBuilder().weakCache().autocommit().asMap();
        //NOTE: very important to save before adding contents to avoid transient storage that spikes memory requirements 
        resource.save(options);
	    return resource;
	}

	public void closeResource(Resource resource) {
		if (resource instanceof PersistentResource) ((PersistentResource) resource).close();
		else resource.unload();
	}

}
