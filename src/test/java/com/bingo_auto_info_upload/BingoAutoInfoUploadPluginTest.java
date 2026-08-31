package com.bingo_auto_info_upload;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BingoAutoInfoUploadPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BingoAutoInfoUploadPlugin.class);
		RuneLite.main(args);
	}
}