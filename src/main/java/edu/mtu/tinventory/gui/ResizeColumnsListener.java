package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.logging.LocalLog;
import java.lang.reflect.Method;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ResizeColumnsListener implements ChangeListener<Skin<?>> {
	private static Method resizeColumnMethod;
	private TableView<?> table;

	/*
	 * Since the method to call will be the same for all instances of this class, best to only
	 * set this up once. Reflection is slow-enough as it is, let's not make it slower by setting
	 * up the same thing multiple times. This will get called on the first time an instance is created.
	 */
	static {
		try {
			resizeColumnMethod = Class.forName("com.sun.javafx.scene.control.skin.TableViewSkin").getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
			resizeColumnMethod.setAccessible(true);
		} catch (ReflectiveOperationException e) {
			LocalLog.exception("Failed to grab method to automatically resize columns! Things will look funky...", e);
		}
	}

	public ResizeColumnsListener(TableView<?> table) {
		this.table = table;
	}

	@Override
	public void changed(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue, Skin<?> newValue) {
		if(newValue != null && resizeColumnMethod != null) {
			Platform.runLater(() -> { // This threads the resizing of columns, which buys us just enough time for everything to not be null.
				for (Object column : table.getColumns()) {
					try {
						resizeColumnMethod.invoke(table.getSkin(), column, -1);
					} catch (ReflectiveOperationException e) {
						LocalLog.exception("Failed to automatically resize column...somehow...", e);
					}
				}
			});
		}
	}
}
