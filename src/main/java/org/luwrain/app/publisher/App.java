/*
   Copyright 2012-2020 Michael Pozhidaev <msp@luwrain.org>

   This file is part of LUWRAIN.

   LUWRAIN is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   LUWRAIN is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.
*/

package org.luwrain.app.publisher;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

import org.luwrain.core.*;
import org.luwrain.core.events.*;
import org.luwrain.controls.*;
import org.luwrain.speech.*;
import org.luwrain.app.base.*;

public final class App extends AppBase<Strings>
{
    static final String LOG_COMPONENT = "publisher";

    private final String arg;
    private Conversations conv = null;
    private AuthLayout authLayout = null;
    private MainLayout mainLayout = null;

    public App()
    {
	this(null);
    }

    public App(String arg)
    {
	super(Strings.NAME, Strings.class, "luwrain.notepad");
	this.arg = arg;
    }

    @Override public boolean onAppInit() throws IOException
    {
	this.conv = new Conversations(getLuwrain(), getStrings());
	this.mainLayout = new MainLayout(this);
		this.authLayout = new AuthLayout(this);
	setAppName(getStrings().appName());
	return true;
    }

    Conversations getConv()
    {
	return this.conv;
    }

    @Override public AreaLayout getDefaultAreaLayout()
    {
	return authLayout.getLayout();
    }

    @Override public boolean onEscape(InputEvent event)
    {
	closeApp();
	return true;
    }
}
