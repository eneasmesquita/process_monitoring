package aut.com.login;

public class HexConverter
{

	private static String byteToHex(byte byte0)
	{
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(toHexChar(byte0 >>> 4 & 0xf));
		stringbuffer.append(toHexChar(byte0 & 0xf));
		return stringbuffer.toString();
	}

	private static char toHexChar(int i)
	{
		if(i >= 0 && i <= 9)
			return (char)(48 + i);
		else
			return (char)(97 + (i - 10));
	}

	public String convertString(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			stringbuffer.append(Integer.toHexString(0x1000 | c).substring(2));
		}

		return stringbuffer.toString();
	}

	public HexConverter()
	{
	}
}
