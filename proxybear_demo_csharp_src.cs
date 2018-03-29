using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace proxySample
{
    class Program
    {
        static void Main(string[] args)
        {
            execHttpTest();
        }

        static void execHttpTest()
        {
            string host = "http://127.0.0.1:28776/";
            Dictionary<string, string> parms = new Dictionary<string,string>();
            string sn = string.Empty;
            //sn = "kcNpUcnnduQ7eTdRTTowSF4o9PlxgR4U";

            //API: test
            Console.WriteLine("API: test result = " + LoadPage(getProxyUrl(host, sn, "test", parms)));

            //API: getLevel
            Console.WriteLine("API: getLevel result = " + LoadPage(getProxyUrl(host, sn, "getLevel", parms)));

            //API: getActiveProxy
            Console.WriteLine("API: getActiveProxy result = " + LoadPage(getProxyUrl(host, sn, "getActiveProxy", parms)));

            //API: getTestedProxy
            Console.WriteLine("API: getTestedProxy result = " + LoadPage(getProxyUrl(host, sn, "getTestedProxy", parms)));

            //API: copyActiveProxy
            Console.WriteLine("API: copyActiveProxy result = " + LoadPage(getProxyUrl(host, sn, "copyActiveProxy", parms)));

            //API: CopyTestedProxy
            Console.WriteLine("API: CopyTestedProxy result = " + LoadPage(getProxyUrl(host, sn, "CopyTestedProxy", parms)));

            //API: copyAllProxy
            Console.WriteLine("API: copyCrawlProxy result = " + LoadPage(getProxyUrl(host, sn, "copyCrawlProxy", parms)));

            //API: removeActiveProxy
            parms.Add("count", "1");
            Console.WriteLine("API: removeActiveProxy result = " + LoadPage(getProxyUrl(host, sn, "removeActiveProxy", parms)));

            //API: removeTestedProxy
            Console.WriteLine("API: removeTestedProxy result = " + LoadPage(getProxyUrl(host, sn, "removeTestedProxy", parms)));

            //API: removeBlockProxy
            Console.WriteLine("API: removeBlockProxy result = " + LoadPage(getProxyUrl(host, sn, "removeBlockProxy", parms)));

            //API: removeAllProxy
            Console.WriteLine("API: removeCrawlProxy result = " + LoadPage(getProxyUrl(host, sn, "removeCrawlProxy", parms)));

            parms.Clear();

            //API: countActive
            Console.WriteLine("API: countActive result = " + LoadPage(getProxyUrl(host, sn, "countActive", parms)));

            //API: countTested
            Console.WriteLine("API: countTested result = " + LoadPage(getProxyUrl(host, sn, "countTested", parms)));

            //API: countBlock
            Console.WriteLine("API: countBlock result = " + LoadPage(getProxyUrl(host, sn, "countBlock", parms)));

            //API: countall
            Console.WriteLine("API: countCrawl result = " + LoadPage(getProxyUrl(host, sn, "countCrawl", parms)));

            //API: returnActiveProxy
            parms.Clear();
            parms.Add("proxy", proxy2LongString("202.101.202.101:8080"));
            Console.WriteLine("API: returnActiveProxy result = " + LoadPage(getProxyUrl(host, sn, "returnActiveProxy", parms)));

            //API: returnBlockProxy
            parms.Clear();
            parms.Add("proxy", proxy2LongString("202.101.202.102:8080"));
            Console.WriteLine("API: returnBlockProxy result = " + LoadPage(getProxyUrl(host, sn, "returnBlockProxy", parms)));


            //API: returnTestedProxy
            parms.Clear();
            parms.Add("proxy", proxy2LongString("202.101.202.103:8080"));
            Console.WriteLine("API: returnTestedProxy result = " + LoadPage(getProxyUrl(host, sn, "returnTestedProxy", parms)));

            Console.ReadKey();
        }

        static string LoadPage(string url)
        {
            HttpWebRequest req = null;
            HttpWebResponse res = null;
            Stream res_stream = null;
            MemoryStream response_stream = null;

            try
            {
                req = (HttpWebRequest)WebRequest.Create(new Uri(url));
                req.ServicePoint.Expect100Continue = false;
                req.Timeout = 30 * 1000;
                req.KeepAlive = false;
                req.Method = "GET";

                res = (HttpWebResponse)req.GetResponse();

                response_stream = new MemoryStream();
                byte[] buf = new byte[1024 * 8];
                res_stream = res.GetResponseStream();

                int  len = res_stream.Read(buf, 0, buf.Length);

                while (len > 0)
                {
                    response_stream.Write(buf, 0, len);
                    len = res_stream.Read(buf, 0, buf.Length);
                }

                res_stream.Close();
                res_stream = null;
                response_stream.Position = 0;

                return System.Text.Encoding.Default.GetString(response_stream.ToArray());
            }
            catch
            {
                return string.Empty;
            }
            finally
            {
                if (res_stream != null) res_stream.Close();
                if (response_stream != null) response_stream.Close();
                if (res != null) res.Close();
                if (req != null) req.Abort();
            }
        }

        static string getProxyUrl(string host, string sn, string method, Dictionary<string, string> parms)
        {
            string parameter = string.Empty;
            foreach (string key in parms.Keys) parameter += (parameter.StartsWith("?") ? "&" : "?") + key + "=" + parms[key];
            if (!string.IsNullOrEmpty(sn)) parameter += (string.IsNullOrEmpty(parameter) ? "?" : "&") + "sn=" + sn;

            return host + method + parameter;
        }

        static string proxy2LongString(string proxy)
        {
            string[] ipx = proxy.Split(':');
            string[] ips = ipx[0].Split('.');

            long result = (long.Parse(ips[0]) << 40) + (long.Parse(ips[1]) << 32) + (long.Parse(ips[2]) << 24) + (long.Parse(ips[3]) << 16) + long.Parse(ipx[1]);
            return result.ToString();
        }
    }
}
