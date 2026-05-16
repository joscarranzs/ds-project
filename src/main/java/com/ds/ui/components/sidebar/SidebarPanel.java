package com.ds.ui.components.sidebar;

import com.ds.ui.components.sidebar.header.HeaderSection;
import com.ds.ui.components.sidebar.navigation.NavSection;
import com.myui.layouts.VBoxLayout;

public class SidebarPanel extends VBoxLayout {

  public SidebarPanel() {
    addElement(new HeaderSection().getVBox());
    addElement(new NavSection().getVBox());
  }
}