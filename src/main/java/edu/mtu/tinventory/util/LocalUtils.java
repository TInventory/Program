package edu.mtu.tinventory.util;

public class LocalUtils {
	// Enum of given operating system
      OperatingSystems os;

    
	/**
	* Constructor
	* Instance of utilties for local file system
	*/
    public LocalUtils() {
        this.os = determineOS();
        
    }
    
	/**
	* Retrieves the OperatinSystem enum for the OS the application is running on.
	* 
	* @return Enum : OperatingSystem the application is running on
	*/
    public OperatingSystems getOperatingSystem() {
        return os;
    }
    
	/**
	* Determines the OS for this running system
	*
	* @return OperatingSystem enum the applicationn is running on
	*/
	// Check if BSD is captured by this
    private OperatingSystems determineOS() {
	// Get OS ID from system properties
        String name = System.getProperty("os.name");
	// Covers most unix like and linux like OS's
        if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
            return OperatingSystems.LINUX;
        }
	// Covers all windows operating systems except DOS (which has no GUI so this program can't even run on it)
        else if (name.contains("window")) {
            return OperatingSystems.WINDOWS;
        }
	// Covers OSX, MacOS, and the predecessor Darwin
        else if(name.contains("mac") || name.contains("win")){
            return OperatingSystems.MAC_OS;
        }
	// Covers Solaris and SunOS systems
        else if(name.contains("sunos") || name.contains("sol")){
            return OperatingSystems.SOLARIS;
        }
	// Return the OS
        return OperatingSystems.UNKNOWN;
    }
}
