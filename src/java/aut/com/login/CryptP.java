package aut.com.login;

import java.util.Random;

public class CryptP
{

	private int key[];

	public CryptP()
	{
		makeKey(5);
	}

	public int[] getKey()
	{
		return key;
	}

	public CryptP(int i)
	{
		makeKey(i);
	}

	public String decrypt(String s)
	{
		String s1 = new String();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			char c1 = c;
			int j = fx(i + 1);
			int k = c1 - j;
			if(k < 0)
				k = k % 256 + 256;
			if(k > 255)
				k %= 256;
			s1 = s1 + (char)k;
		}

		return s1;
	}

	public String encrypt(String s)
	{
		String s1 = new String();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			char c1 = c;
			int j = fx(i + 1);
			int k = c1 + j;
			if(k > 255)
				k %= 256;
			s1 = s1 + (char)k;
		}

		return s1;
	}

	private int fx(int i)
	{
		int j = 0;
		int k = key.length;
		int l = k;
		for(int i1 = 0; i1 < k; i1++)
		{
			j += key[i1] * (int)Math.pow(i, l);
			l--;
		}

		return j;
	}

	private void makeKey(int i)
	{
		key = new int[i];
		Random random = new Random();
		for(int k = 0; k < i; k++)
		{
			int j = random.nextInt();
			if(j < 0)
				j = -j;
			key[k] = j % 10;
			if(key[k] == 0)
				key[k]++;
		}

	}

	public String toHexString(String s)
	{
		HexConverter hexconverter = new HexConverter();
		return hexconverter.convertString(s);
	}
}
