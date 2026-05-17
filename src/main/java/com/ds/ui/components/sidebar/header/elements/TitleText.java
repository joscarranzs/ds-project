package com.ds.ui.components.sidebar.header.elements;

import com.myui.controls.TextLabel;

public class TitleText extends TextLabel {
  public TitleText() {
    super("Gestor delivery");
    setInlineStyle(
        "-fx-font-size: 18px; " +
        "-fx-font-weight: bold; " +
        "-fx-text-fill: #1A1A1A; " +
        "-fx-padding: 0 0 4px 0;"
    );
  }
}
