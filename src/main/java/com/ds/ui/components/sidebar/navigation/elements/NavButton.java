package com.ds.ui.components.sidebar.navigation.elements;

import com.myui.controls.Button;

public class NavButton extends Button {

  private boolean active;

  public static final String ACTIVE_STYLE =
      "-fx-background-color: #E3EAF5; " +
      "-fx-text-fill: #1A1A1A; " +
      "-fx-font-weight: bold; " +
      "-fx-padding: 10px 16px; " +
      "-fx-font-size: 14px; " +
      "-fx-background-radius: 8px; " +
      "-fx-alignment: center-left; " +
      "-fx-border-color: transparent transparent transparent #000000; " +
      "-fx-border-width: 0 0 0 3px;";

  private static final String BASE_STYLE =
      "-fx-background-color: transparent; " +
      "-fx-text-fill: #676B6E; " +
      "-fx-padding: 10px 16px; " +
      "-fx-font-size: 14px; " +
      "-fx-background-radius: 8px; " +
      "-fx-alignment: center-left;";

  private static final String HOVER_STYLE =
      "-fx-background-color: #E3EAF5; " +
      "-fx-text-fill: #676B6E; " +
      "-fx-padding: 10px 16px; " +
      "-fx-font-size: 14px; " +
      "-fx-background-radius: 8px; " +
      "-fx-alignment: center-left;";

  public NavButton(String text) {
    super(text);
    setInlineStyle(BASE_STYLE);
    getButton().setMaxWidth(Double.MAX_VALUE);

    getButton().setOnMouseEntered(e -> {
      if (!active) getButton().setStyle(HOVER_STYLE);
    });
    getButton().setOnMouseExited(e -> {
      getButton().setStyle(active ? ACTIVE_STYLE : BASE_STYLE);
    });
  }

  public NavButton withActiveStyle() {
    setActive(true);
    return this;
  }

  public void setActive(boolean active) {
    this.active = active;
    setInlineStyle(active ? ACTIVE_STYLE : BASE_STYLE);
  }

  public boolean isActive() {
    return active;
  }
}
