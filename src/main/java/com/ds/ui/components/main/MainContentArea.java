package com.ds.ui.components.main;

import com.myui.controls.TextLabel;
import com.myui.layouts.VBoxLayout;

public class MainContentArea extends VBoxLayout {

  public MainContentArea() {
    getVBox().setPrefWidth(600);
    addExampleText();
  }

  private void addExampleText() {
    TextLabel exampleText = new TextLabel("This is the main content area.");
    addElement(exampleText.getLabel());
  }
}
