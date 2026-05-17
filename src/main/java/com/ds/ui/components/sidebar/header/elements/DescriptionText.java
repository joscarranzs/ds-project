package com.ds.ui.components.sidebar.header.elements;

import com.myui.controls.TextLabel;

public class DescriptionText extends TextLabel {
  public DescriptionText() {
    super("v1.0");
    setInlineStyle(
        "-fx-font-size: 11px; " +
        "-fx-text-fill: #8D9395;"
    );
  }
}
