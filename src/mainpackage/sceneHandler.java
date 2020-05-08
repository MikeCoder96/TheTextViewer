package mainpackage;

import mainpackage.sceneHandler;

public class sceneHandler {
	private static sceneHandler thiscene = null;
	
	public static sceneHandler getInstance() {
		  if (thiscene == null)
			  thiscene = new sceneHandler(); 
		    return thiscene;
	}
	
}
