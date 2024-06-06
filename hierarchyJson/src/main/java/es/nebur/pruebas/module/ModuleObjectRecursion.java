package es.nebur.pruebas.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class ModuleObjectRecursion extends Module {

	@Override
	public String getModuleName() {
		return "json-object-recursion";
	}

	@Override
	public Version version() {
		return new Version(10, 0, 1, "snapshot", "groupId", "artifactID");
	}

	@Override
	public void setupModule(SetupContext context) {
		
		context.addSerializers(new ObjectRecursionSerializer());
		
	}

}
