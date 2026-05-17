package com.ds.ui.components.sidebar.navigation.elements;

import com.myui.controls.Button;

public class NavButton extends Button {

  public static final String ACTIVE_STYLE =
      "-fx-background-color: #E3EAF5; " +
      "-fx-text-fill: #1A1A1A; " +
      "-fx-padding: 12px 16px; " +
      "-fx-font-size: 15px; " +
      "-fx-background-radius: 8px; " +
      "-fx-alignment: center-left; " +
      "-fx-border-color: transparent transparent transparent #000000; " +
      "-fx-border-width: 0 0 0 3px;";

  private static final String BASE_STYLE =
      "-fx-background-color: #FFFFFF; " +
      "-fx-text-fill: #1A1A1A; " +
      "-fx-padding: 12px 16px; " +
      "-fx-font-size: 15px; " +
      "-fx-background-radius: 8px; " +
      "-fx-alignment: center-left;";

  public NavButton(String text) {
    super(text);
    setInlineStyle(BASE_STYLE);
    getButton().setMaxWidth(Double.MAX_VALUE);
  }

  public NavButton withActiveStyle() {
    setInlineStyle(ACTIVE_STYLE);
    return this;
  }
}
