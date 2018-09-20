package com.firesend;

import android.view.accessibility.*;
import android.os.*;

public interface Controler
{
	public void notifyHandlerControl(Handler handlerControl)
	public void notifyTargetGet(Target target)
	public void notifySendCountChanged(int sendCount)
	public void notifyIsSending(boolean isSending)
}
