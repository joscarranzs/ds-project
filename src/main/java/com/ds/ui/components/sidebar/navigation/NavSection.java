package com.ds.ui.components.sidebar.navigation;

import com.ds.ui.components.sidebar.navigation.elements.NavButton;
import com.myui.layouts.VBoxLayout;

public class NavSection extends VBoxLayout {

  public NavSection() {
    addElement(new NavButton("Inicio").withActiveStyle().getButton());
    addElement(new NavButton("Entregas").getButton());
    addElement(new NavButton("Repartidores").getButton());
    addElement(new NavButton("Métricas").getButton());
    setInlineStyle("-fx-padding: 10px 15px; -fx-spacing: 10px;");
  }
}
