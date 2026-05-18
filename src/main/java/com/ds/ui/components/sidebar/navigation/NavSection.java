package com.ds.ui.components.sidebar.navigation;

import java.util.Arrays;
import java.util.List;
import com.ds.ui.components.sidebar.navigation.elements.NavButton;
import com.myui.layouts.VBoxLayout;

public class NavSection extends VBoxLayout {

  private final List<NavButton> buttons;

  public NavSection() {
    buttons = Arrays.asList(
        new NavButton("Inicio"),
        new NavButton("Entregas"),
        new NavButton("Repartidores"),
        new NavButton("Métricas")
    );

    buttons.get(0).setActive(true);

    for (NavButton btn : buttons) {
      btn.getButton().setOnAction(e -> selectButton(btn));
      addElement(btn.getButton());
    }

    setInlineStyle("-fx-padding: 10px 15px; -fx-spacing: 10px;");
  }

  private void selectButton(NavButton selected) {
    for (NavButton btn : buttons) {
      btn.setActive(btn == selected);
    }
  }
}
