package com.ds.ui.components.sidebar.header;

import com.ds.ui.components.sidebar.header.elements.TitleText;
import com.ds.ui.components.sidebar.header.elements.DescriptionText;
import com.myui.layouts.VBoxLayout;

public class HeaderSection extends VBoxLayout {

  public HeaderSection() {
    addElement(new TitleText().getLabel());
    addElement(new DescriptionText().getLabel());
  }
}