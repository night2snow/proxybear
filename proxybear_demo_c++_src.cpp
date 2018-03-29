#include "stdafx.h"
#include <stdio.h>
#include <windows.h>
#include <wininet.h>
#include <string>
#include <vector>

#pragma comment(lib, "Wininet.lib") 

using namespace std;

void execMethod(string&, string, string&, string);
vector<string> split(const string&, string);
string proxy2long_string(string);


int _tmain(int argc, _TCHAR* argv[])
{
	string host = "http://127.0.0.1:28776/";
	string sn = "";
	//string sn = "kcNpUcnnduQ7eTdRTTowSF4o9PlxgR4U";

	//API: test
	execMethod(host, "test", sn, "");
	//API: getlevel
	execMethod(host, "getlevel", sn, "");
	//API: getActiveProxy
	execMethod(host, "getActiveProxy", sn, "");
	//API: getTestedProxy
	execMethod(host, "getTestedProxy", sn, "");
	//API: copyActiveProxy
	execMethod(host, "copyActiveProxy", sn, "");
	//API: copyTestedProxy
	execMethod(host, "copyTestedProxy", sn, "");
	//API: copyAllProxy
	execMethod(host, "copyCrawlProxy", sn, "");
	//API: removeActiveProxy
	execMethod(host, "removeActiveProxy", sn, "?count=1");
	//API: removeTestedProxy
	execMethod(host, "removeTestedProxy", sn, "?count=1");
	//API: removeBlockProxy
	execMethod(host, "removeBlockProxy", sn, "?count=1");
	//API: removeAllProxy
	execMethod(host, "removeCrawlProxy", sn, "?count=1");
	//API: countActive
	execMethod(host, "countActive", sn, "");
	//API: countTested
	execMethod(host, "countTested", sn, "");
	//API: countBlock
	execMethod(host, "countBlock", sn, "");
	//API: countall
	execMethod(host, "countCrawl", sn, "");
	//API: returnActiveProxy
	execMethod(host, "returnActiveProxy", sn, "?proxy=" + proxy2long_string("202.101.202.101:8080"));
	//API: returnBlockProxy
	execMethod(host, "returnBlockProxy", sn, "?proxy=" + proxy2long_string("202.101.202.102:8080"));
	//API: returnTestedProxy
	execMethod(host, "returnTestedProxy", sn, "?proxy=" + proxy2long_string("202.101.202.103:8080"));

	system("pause");
	return 0;
}

void execMethod(string& host, string method, string& sn, string params)
{
	string rurl = host;
	rurl.append(method);
	if (!params.empty()) rurl.append(params);
	if (!sn.empty())
	{
		if (params.empty()) rurl.append("?sn=" + sn);
		else rurl.append("&sn=" + sn);
	}

	HINTERNET hSession = InternetOpen(NULL, INTERNET_OPEN_TYPE_PRECONFIG, NULL, NULL, 0);
	if (hSession != NULL)
	{
		HINTERNET hHttp = InternetOpenUrl(hSession, rurl.c_str(), NULL, 0, INTERNET_FLAG_DONT_CACHE, 0);
		if (hHttp != NULL)
		{
			printf("API: %s result = ", method.c_str());
			BYTE Temp[1024];
			ULONG Number = 1;

			while (Number > 0)
			{
				InternetReadFile(hHttp, Temp, 1024 - 1, &Number);
				Temp[Number] = '\0';
				printf("%s\n", Temp);
			}

			InternetCloseHandle(hHttp);
			hHttp = NULL;
		}
		InternetCloseHandle(hSession);
		hSession = NULL;
	}
}

string proxy2long_string(string proxy)
{
	vector<string> ipx = split(proxy, ":");
	vector<string> ips = split(ipx[0], ".");

	long long result = (stoll(ips[0]) << 40) + (stoll(ips[1]) << 32) + (stoll(ips[2]) << 24) + (stoll(ips[3]) << 16) + stoll(ipx[1]);

	return to_string(result);
}

vector<string> split(const string& src, string sps)
{
	vector<string> result;
	int spslen = sps.size();
	int pos = 0, index = -1;

	while (-1 != (index = src.find(sps, pos)))
	{
		result.push_back(src.substr(pos, index - pos));
		pos = index + spslen;
	}
	string lasts = src.substr(pos);
	if (!lasts.empty()) result.push_back(lasts);

	return result;
}
