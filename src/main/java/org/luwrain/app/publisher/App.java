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
    static final String LOG_COMPONENT = "notepad";
    static private final String DEFAULT_CHARSET = "UTF-8";
    static private final String NATURAL_MODE_CORRECTOR_HOOK = "luwrain.notepad.mode.natural";
    static private final String PROGRAMMING_MODE_CORRECTOR_HOOK = "luwrain.notepad.mode.programming";

    enum Mode { NONE, NATURAL, PROGRAMMING };

    File file = null;
    boolean modified = false;
    String charset = DEFAULT_CHARSET;
    String lineSeparator = System.lineSeparator();
    Mode mode = Mode.NONE;
    final EditUtils.ActiveCorrector corrector;
    boolean speakIndent = false;

    private FutureTask narratingTask = null; 
    private final String arg;
    private Conversations conv = null;
    private MainLayout mainLayout = null;

    public App()
    {
	this(null);
    }

    public App(String arg)
    {
	super(Strings.NAME, Strings.class, "luwrain.notepad");
	this.arg = arg;
	this.corrector = new EditUtils.ActiveCorrector();
    }

    @Override public boolean onAppInit() throws IOException
    {
	this.conv = new Conversations(getLuwrain(), getStrings());
	this.mainLayout = new MainLayout(this);
	setAppName(getStrings().appName());
	return true;
    }

    boolean onInputEvent(Area area, InputEvent event, Runnable closing)
    {
	NullCheck.notNull(area, "area");
	if (event.isSpecial())
	    switch(event.getSpecial())
	    {
	    case ESCAPE:
		if (closing != null)
		    closing.run(); else
		    closeApp();
		return true;
	    }
	return super.onInputEvent(area, event);
    }

    @Override public boolean onInputEvent(Area area, InputEvent event)
    {
	NullCheck.notNull(area, "area");
	NullCheck.notNull(event, "event");
	return onInputEvent(area, event, null);
    }

    Conversations getConv()
    {
	return this.conv;
    }

    @Override public AreaLayout getDefaultAreaLayout()
    {
	return mainLayout.getLayout();
    }

}
