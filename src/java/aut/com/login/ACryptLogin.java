package aut.com.login;

public class ACryptLogin
{

	private static final long serialVersionUID = 1L;

	public ACryptLogin()
	{
	}

	public String encrypt(String s)
	{
		CryptP cryptp = new CryptP();
		HexConverter hexconverter = new HexConverter();
		int ai[] = cryptp.getKey();
		String s1 = new String();
		for(int i = 0; i < ai.length; i++)
		{
			s1 = s1 + ai[i];
			if(i < ai.length - 1)
				s1 = s1 + '-';
		}

		String s2 = hexconverter.convertString(s1);
		String s3 = cryptp.toHexString(cryptp.encrypt(s));
		return s2 + s3;
	}
}
