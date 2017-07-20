package com.asela;

import java.util.Base64;

import org.junit.Test;

public class Base64Test {

	@Test
	public void base64Test() throws Exception {
		
		String enc = Base64.getEncoder().encodeToString("ManManMan".getBytes());
		System.out.println(enc);
		
		String s = "MIIDLzCCAhegAwIBAgIERUas2jANBgkqhkiG9w0BAQsFADBIMQowCAYDVQQGEwFB" + 
				"MQowCAYDVQQIEwFBMQowCAYDVQQHEwFBMQowCAYDVQQKEwFBMQowCAYDVQQLEwFB" + 
				"MQowCAYDVQQDEwFBMB4XDTE3MDcxOTAzMjAxNFoXDTE3MTAxNzAzMjAxNFowSDEK" + 
				"MAgGA1UEBhMBQTEKMAgGA1UECBMBQTEKMAgGA1UEBxMBQTEKMAgGA1UEChMBQTEK" + 
				"MAgGA1UECxMBQTEKMAgGA1UEAxMBQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC" + 
				"AQoCggEBAINmWzO0j3nJeaFR7nHvIQ8UNvhGDmPa3N1I7n+c1v3AE6NwMvIrOsmi" + 
				"DQXCj0m7CzWPIA1+j0CMYjweDolzBvvczkStX4vgBfmtPwFhEEL2UgqIxwh66fYZ" + 
				"eMkZlWtmftWd11IpjvefWthSIY89qF1Z2qJ07ceoHzsuDTBc6F7xy+SVNhbBGBDo" + 
				"kNAz/8pR1PfvsHDpulJ9FP3b2e3ePHWsO3ZkrXf0AMO8lF3qCdIctxvLVmWTI1+w" + 
				"upq97gpaAiAvCeGQJBrdZM9iyQcuJBxyHwo0CD9BoS27ItGfx933zJz4GnOaSUA4" + 
				"NE/73rezBsReQq9yeLw2Z9HSga9Cdj8CAwEAAaMhMB8wHQYDVR0OBBYEFDVOe9KN" + 
				"3jFTliL1/dCHmTfJjPAEMA0GCSqGSIb3DQEBCwUAA4IBAQA+gFgxUnJVN0GF4Bd3" + 
				"blHITV8G23xuWmzp7IC4lAL6aNi9PUDHrMc9TLWUxyh5/MNUsXyaEPiWDP/XNgXq" + 
				"T4E29VUFimk4b8+3NsuWAhfP4RM5nu1tTLksBDhQjW79NoYgfvmVq/sUdniyzyzf" + 
				"yMnvKy1756NbF/xkFox5JzNyxlyiwYImHxkr690jjl7S1TGZVEtGNjVlg+K2pCJ5" + 
				"MX0ATx49GJYK8CP0Yfegt1C1qiXQspv36B3TpqEiPV8/0h89OaBF/i2PEiRUInlR" + 
				"4ypsozo91igfQW/DOuHIDt2TRjATmw0KAg7F96PlFp5spoEnT60hR6iPi+wcHljZ" + 
				"nRp2";
		System.out.println(new String(Base64.getDecoder().decode(s.getBytes())));
	}
}
