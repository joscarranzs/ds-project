package com.ds.ui.components.sidebar.navigation;

import com.ds.ui.components.sidebar.navigation.elements.NavButton;
import com.myui.layouts.VBoxLayout;

public class NavSection extends VBoxLayout {

  public NavSection() {
    addElement(new NavButton("Dashboard").getButton());
    addElement(new NavButton("Pedidos").getButton());
    addElement(new NavButton("Rutas").getButton());
    addElement(new NavButton("Clientes").getButton());
    addElement(new NavButton("Repartidores").getButton());
  }
}
