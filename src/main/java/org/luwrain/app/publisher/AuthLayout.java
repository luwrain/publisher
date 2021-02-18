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
import java.util.concurrent.atomic.*;
import java.io.*;

import org.luwrain.core.*;
import org.luwrain.core.events.*;
import org.luwrain.core.queries.*;
import org.luwrain.controls.*;
import org.luwrain.script.*;
import org.luwrain.app.base.*;

final class AuthLayout extends LayoutBase
{
    private final App app;
    private final WizardArea wizardArea;

    AuthLayout(App app)
    {
	NullCheck.notNull(app, "app");
	this.app = app;
	this.wizardArea = new WizardArea(new DefaultControlContext(app.getLuwrain())){
		@Override public boolean onInputEvent(InputEvent event)
		{
		    NullCheck.notNull(event, "event");
		    if (app.onInputEvent(this, event))
			return true;
		    return super.onInputEvent(event);
		}

				@Override public boolean onSystemEvent(SystemEvent event)
		{
		    NullCheck.notNull(event, "event");
		    if (app.onSystemEvent(this, event))
			return true;
		    return super.onSystemEvent(event);
		}
		@Override public boolean onAreaQuery(AreaQuery query)
		{
		    NullCheck.notNull(query, "query");
		    if (app.onAreaQuery(this, query))
			return true;
		    return super.onAreaQuery(query);
		}
	    };
	this.wizardArea.newFrame()
	.addText("Добро пожаловать в приложение Издатель!")
	.addText("Сейчас необходимо подключиться к сервису LUWRAIN Books. Для этого на следующем шаге необходимо указать адрес электронной почты и пароль зарегистрированного пользователя.")
	.addText("Если у вас в настоящий момент нет учётной записи для сервиса LUWRAIN Books, то новый пользователь будет добавлен автоматически. В этом случае потребуется ввести проверочный код подтверждения, который позволяет убедиться, что указанный адрес электронной почты принадлежит именно вам. ННажмите Enter на строке со словом \"Продолжить\".")
	.addClickable("Продолжить", (values)->{ return false; });
    }

    AreaLayout getLayout()
    {
	return new AreaLayout(wizardArea);
    }
    }
