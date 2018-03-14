package aut.com.login;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   jlogin.java

import java.io.PrintStream;

class myThread extends Thread
{

	public void run()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException interruptedexception)
		{
			System.err.println(interruptedexception.toString());
		}
	}

	myThread()
	{
	}
}
