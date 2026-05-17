package com.ds.ui;

import com.myui.window.MainWindow;
import com.myui.layouts.HBoxLayout;
import com.ds.ui.components.main.MainContentArea;
import com.ds.ui.components.sidebar.SidebarPanel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación JavaFX del sistema de entregas.
 * <p>
 * Compone la interfaz principal usando el framework myui:
 * un layout horizontal con la barra lateral de navegación
 * y el área de contenido principal, todo envuelto en un MainWindow.
 * </p>
 */
public class AppLauncher extends Application {

  @Override
  public void start(Stage primaryStage) {
    HBoxLayout mainLayout = new HBoxLayout();

    mainLayout.addElement(new SidebarPanel().getVBox());
    mainLayout.addElement(new MainContentArea().getVBox());

    MainWindow mainWindow = new MainWindow(primaryStage, mainLayout.getHBox());

    mainWindow.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
