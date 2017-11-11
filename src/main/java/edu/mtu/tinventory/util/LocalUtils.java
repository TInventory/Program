package edu.mtu.tinventory.util;

public class LocalUtils {

      OperatingSystems os;

    
    public LocalUtils() {
        this.os = determineOS();
        
        
    }
    
    
    public OperatingSystems getOperatingSystem() {
        return os;
    }
    
    private OperatingSystems determineOS() {
        String name = System.getProperty("os.name");
        if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
            return OperatingSystems.LINUX;
        }
        else if (name.contains("window")) {
            return OperatingSystems.WINDOWS;
        }
        else if(name.contains("mac") || name.contains("win")){
            return OperatingSystems.MAC_OS;
        }
        else if(name.contains("sunos") || name.contains("sol")){
            return OperatingSystems.SOLARIS;
        }
        return OperatingSystems.UNKNOWN;
    }
}
