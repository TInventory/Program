package edu.mtu.tinventory.gui;

import javafx.scene.image.Image;

public class IconLoader {

    private Image icon;
 
    public Image getIcon() {
        this.load();
        return icon;
    }
    
    private void load() {
        icon = new Image("file:src/main/resources/Icon.png");
    }
}
